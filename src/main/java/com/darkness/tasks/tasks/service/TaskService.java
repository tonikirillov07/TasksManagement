package com.darkness.tasks.tasks.service;

import com.darkness.tasks.TaskMapper;
import com.darkness.tasks.exceptions.*;
import com.darkness.tasks.tasks.TaskSearchFilter;
import com.darkness.tasks.tasks.entity.TaskEntity;
import com.darkness.tasks.tasks.repositories.TasksRepository;
import com.darkness.tasks.utils.TaskStatus;
import com.darkness.tasks.tasks.entity.Task;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TasksRepository tasksRepository;
    private final TaskMapper taskMapper;
    private final int maxInProgressTasks, defaultPageSize;

    public TaskService(TasksRepository tasksRepository,
                       TaskMapper taskMapper,
                       @Qualifier("maxInProgressTasks") int maxInProgressTasks,
                       @Qualifier("defaultPageSize") int defaultPageSize) {
        this.tasksRepository = tasksRepository;
        this.taskMapper = taskMapper;
        this.maxInProgressTasks = maxInProgressTasks;
        this.defaultPageSize = defaultPageSize;
    }

    public @NotNull Task findTaskById(@NotNull Long id){
        TaskEntity taskEntity = tasksRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        log.info("Found task by id {}", id);

        return taskMapper.entityToDomain(taskEntity);
    }

    public @NotNull List<Task> searchTasksByFilter(@NotNull TaskSearchFilter taskSearchFilter){
        List<Task> allTasks = tasksRepository.searchAllByFilter(
                taskSearchFilter.creatorId(),
                taskSearchFilter.assignedUserId(),
                taskSearchFilter.taskStatus(),
                taskSearchFilter.taskPriority(),
                taskSearchFilter.getPageable(defaultPageSize)).stream().map(taskMapper::entityToDomain).toList();

        log.info("Found tasks by filter {}: {}", taskSearchFilter, allTasks.size());

        return allTasks;
    }

    public @NotNull Task createNewTask(@NotNull Task task) {
        if(task.hasStatus())
            throw new IllegalArgumentException("Task status should be null. Current status: " + task.taskStatus());

        if(task.isWrongDates())
            throw new TaskWrongDateException();

        if(task.hasDoneTime())
            throw new IllegalArgumentException("Done time should be empty");

        TaskEntity taskEntityToSave = taskMapper.domainToEntity(task);
        taskEntityToSave.setTaskStatus(TaskStatus.CREATED);

        Task taskDomain = taskMapper.entityToDomain(tasksRepository.save(taskEntityToSave));

        log.info("Created new task: {}", taskDomain);

        return taskDomain;
    }

    public @NotNull Task updateTask(@NotNull Long id, @NotNull Task taskToUpdate) {
        Task existingTask = taskMapper.entityToDomain(tasksRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));

       if(existingTask.isDone() && taskToUpdate.isDone())
           throw new IllegalStateException("Task with status DONE cannot be modify");

       if(!taskToUpdate.hasStatus())
           throw new IllegalArgumentException("Task must have the status");

       if(taskToUpdate.isWrongDates())
           throw new TaskWrongDateException();

       TaskEntity updatedTaskEntity = new TaskEntity(id,
               taskToUpdate.creatorId(),
               taskToUpdate.assignedUserId(),
               taskToUpdate.taskStatus(),
               taskToUpdate.createDate(),
               taskToUpdate.deadlineDate(),
               taskToUpdate.doneDateTime(),
               taskToUpdate.priority());

       Task updatedTask = taskMapper.entityToDomain(tasksRepository.save(updatedTaskEntity));
       log.info("Updated task with id {}", id);

        return updatedTask;
    }

    public void deleteTask(@NotNull Long id) {
        if(tasksRepository.existsById(id)){
            tasksRepository.deleteById(id);
            log.info("Deleted task with id {}", id);
        }else
            throw new TaskNotFoundException(id);
    }

    public void startTask(@NotNull Long id) {
        TaskEntity taskEntity = tasksRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        Task domainTask = taskMapper.entityToDomain(taskEntity);

        if(domainTask.hasNotAssignedUser())
            throw new IllegalArgumentException("Assigned user id cannot be null. Task id: " + id);

        int activeTasksCount = tasksRepository.findAllByAssignedUserIdAndTaskStatus(domainTask.assignedUserId(), TaskStatus.IN_PROGRESS).size();
        if(activeTasksCount > maxInProgressTasks)
            throw new TooMuchInProgressTasksException(activeTasksCount, maxInProgressTasks, domainTask.assignedUserId());

        if(domainTask.isStarted())
            throw new TaskIsAlreadyInProgress(id);

        tasksRepository.setTaskStatus(id, TaskStatus.IN_PROGRESS);
        log.info("Changed task status to {}. Task id: {}", TaskStatus.IN_PROGRESS, id);
    }

    public void completeTask(@NotNull Long id) {
        TaskEntity taskEntity = tasksRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        Task domainTask = taskMapper.entityToDomain(taskEntity);

        if(domainTask.isDone())
            throw new TaskAlreadyDoneException(id);

        taskEntity.setDoneDateTime(LocalDateTime.now());
        taskEntity.setTaskStatus(TaskStatus.DONE);

        tasksRepository.save(taskEntity);
        log.info("Task with id {} marked as DONE", id);
    }
}
