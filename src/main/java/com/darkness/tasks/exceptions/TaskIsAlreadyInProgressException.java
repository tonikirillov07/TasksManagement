package com.darkness.tasks.exceptions;

public class TaskIsAlreadyInProgressException extends RuntimeException {
    public TaskIsAlreadyInProgressException(Long taskId) {
        super("Task with id " + taskId + " cannot be started because it already is");
    }
}
