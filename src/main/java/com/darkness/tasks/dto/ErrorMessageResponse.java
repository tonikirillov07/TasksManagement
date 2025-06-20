package com.darkness.tasks.dto;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public record ErrorMessageResponse(@NotNull String errorMessage,
                                   @NotNull String detailedErrorMessage,
                                   @NotNull LocalDateTime errorTime) {
    @Contract("_, _ -> new")
    public static @NotNull ErrorMessageResponse of(String errorMessage, String detailedErrorMessage){
        return new ErrorMessageResponse(errorMessage, detailedErrorMessage, LocalDateTime.now());
    }
}
