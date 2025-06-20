package com.darkness.tasks.tasks.entity;

import com.darkness.tasks.utils.TaskPriority;
import com.darkness.tasks.utils.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.jetbrains.annotations.Contract;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task(@Null(message = "Task ID must be null")
                   Long id,
                   @NotNull(message = "Creator ID cannot be null")
                   Long creatorId,
                   @NotNull(message = "Assigned User ID cannot be null")
                   Long assignedUserId,
                   TaskStatus taskStatus,
                   @FutureOrPresent
                   @NotNull(message = "Create Date cannot be null")
                   LocalDate createDate,
                   @FutureOrPresent
                   @NotNull(message = "Deadline Date cannot be null")
                   LocalDate deadlineDate,
                   LocalDateTime doneDateTime,
                   @NotNull(message = "Task Priority cannot be null")
                   TaskPriority priority) {

    @Contract("_, _, _, _, _, _, _, _ -> new")
    public static @org.jetbrains.annotations.NotNull Task of(
            Long id,
            Long creatorId,
            Long assignedUserId,
            TaskStatus taskStatus,
            LocalDate createDate,
            LocalDate deadlineDate,
            LocalDateTime doneDateTime,
            TaskPriority priority){

        return new Task(id, creatorId, assignedUserId, taskStatus, createDate, deadlineDate, doneDateTime, priority);
    }

    public boolean hasStatus(){
        return taskStatus != null;
    }

    public boolean hasDoneTime(){
        return doneDateTime != null;
    }

    public boolean isDone(){
        return taskStatus == TaskStatus.DONE;
    }

    public boolean isStarted() {
        return taskStatus == TaskStatus.IN_PROGRESS;
    }

    public boolean isWrongDates(){
        return deadlineDate.isBefore(createDate);
    }

    public boolean hasNotAssignedUser() {
        return assignedUserId == null;
    }
}

