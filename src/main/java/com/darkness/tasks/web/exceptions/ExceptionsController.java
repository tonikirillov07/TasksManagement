package com.darkness.tasks.web.exceptions;

import com.darkness.tasks.dto.ErrorMessageResponse;
import com.darkness.tasks.exceptions.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {
    private static final Logger log = LoggerFactory.getLogger(ExceptionsController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> handleServerInternalException(@NotNull Exception e) {
        log.error("Internal Server Error", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(ErrorMessageResponse.of("Internal server error", e.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleTaskNotFoundException(@NotNull TaskNotFoundException e) {
        log.error("Not Found", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorMessageResponse.of("Not Found", e.getMessage()));
    }

    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            TooMuchInProgressTasksException.class,
            MethodArgumentNotValidException.class,
            TaskWrongDateException.class,
            TaskAlreadyDoneException.class,
            TaskIsAlreadyInProgressException.class
    })
    public ResponseEntity<ErrorMessageResponse> handleBadRequestException(@NotNull Exception e) {
        log.error("Bad Request", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessageResponse.of("Bad Request", e.getMessage()));
    }
}
