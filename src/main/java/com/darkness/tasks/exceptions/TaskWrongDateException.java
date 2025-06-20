package com.darkness.tasks.exceptions;

public class TaskWrongDateException extends RuntimeException {
    public TaskWrongDateException() {
        super("Task deadline must be after creation time");
    }
}
