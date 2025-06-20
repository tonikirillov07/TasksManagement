package com.darkness.tasks.web.controller;

import com.darkness.tasks.tasks.TaskSearchFilter;
import com.darkness.tasks.utils.TaskPriority;
import com.darkness.tasks.utils.TaskStatus;
import com.darkness.tasks.utils.logging.Loggable;
import com.darkness.tasks.tasks.entity.Task;
import com.darkness.tasks.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @Loggable
    public ResponseEntity<List<Task>> findTasksByFilter(
            @RequestParam(name = "creatorId", required = false) Long creatorId,
            @RequestParam(name = "assignedUserId", required = false) Long assignedUserId,
            @RequestParam(name = "taskStatus", required = false) TaskStatus taskStatus,
            @RequestParam(name = "taskPriority", required = false) TaskPriority taskPriority,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
            ){
        TaskSearchFilter filter = TaskSearchFilter.of(
                creatorId,
                assignedUserId,
                taskStatus,
                taskPriority,
                pageSize,
                pageNumber);

        return ResponseEntity.ok(taskService.searchTasksByFilter(filter));
    }

    @GetMapping("/{id}")
    @Loggable
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id){
        return ResponseEntity.ok(taskService.findTaskById(id));
    }

    @PostMapping
    @Loggable
    public ResponseEntity<Task> createNewTask(@RequestBody @Valid Task task){
        return ResponseEntity.ok(taskService.createNewTask(task));
    }

    @PostMapping("/{id}/start")
    @Loggable
    public ResponseEntity<Void> startTask(@PathVariable("id") Long id){
        taskService.startTask(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/complete")
    @Loggable
    public ResponseEntity<Void> completeTask(@PathVariable("id") Long id){
        taskService.completeTask(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Loggable
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody @Valid Task updatedTask){
        return ResponseEntity.ok(taskService.updateTask(id, updatedTask));
    }

    @DeleteMapping("/{id}")
    @Loggable
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id){
        taskService.deleteTask(id);

        return ResponseEntity.ok().build();
    }
}
