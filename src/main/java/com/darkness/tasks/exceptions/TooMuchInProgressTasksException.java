package com.darkness.tasks.exceptions;

public class TooMuchInProgressTasksException extends RuntimeException {
    public TooMuchInProgressTasksException(Integer inProgressTasks, Integer maxInProgressTasks, Long assignedUserId) {
        super("User with id %d have too much in progress tasks (> %d). In progress currently: %d"
                .formatted(assignedUserId, maxInProgressTasks, inProgressTasks));
    }
}
