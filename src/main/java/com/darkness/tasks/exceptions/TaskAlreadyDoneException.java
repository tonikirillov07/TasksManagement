package com.darkness.tasks.exceptions;

import org.jetbrains.annotations.NotNull;

public class TaskAlreadyDoneException extends RuntimeException {
    public TaskAlreadyDoneException(@NotNull Long taskId) {
        super("Task cannot be done because it already is. Task id: " + taskId);
    }
}
