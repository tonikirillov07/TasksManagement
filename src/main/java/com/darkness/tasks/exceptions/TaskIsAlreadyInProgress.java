package com.darkness.tasks.exceptions;

public class TaskIsAlreadyInProgress extends RuntimeException {
    public TaskIsAlreadyInProgress(Long taskId) {
        super("Task with id " + taskId + " cannot be started because it already is");
    }
}
