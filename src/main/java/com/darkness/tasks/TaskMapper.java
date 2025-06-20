package com.darkness.tasks;

import com.darkness.tasks.tasks.entity.Task;
import com.darkness.tasks.tasks.entity.TaskEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public @NotNull Task entityToDomain(@NotNull TaskEntity taskEntity){
        return Task.of(
                taskEntity.getId(),
                taskEntity.getCreatorId(),
                taskEntity.getAssignedUserId(),
                taskEntity.getTaskStatus(),
                taskEntity.getCreateDate(),
                taskEntity.getDeadlineDate(),
                taskEntity.getDoneDateTime(),
                taskEntity.getPriority()
        );
    }

    public @NotNull TaskEntity domainToEntity(@NotNull Task taskDomain){
        return new TaskEntity(
                taskDomain.id(),
                taskDomain.creatorId(),
                taskDomain.assignedUserId(),
                taskDomain.taskStatus(),
                taskDomain.createDate(),
                taskDomain.deadlineDate(),
                taskDomain.doneDateTime(),
                taskDomain.priority()
        );
    }
}
