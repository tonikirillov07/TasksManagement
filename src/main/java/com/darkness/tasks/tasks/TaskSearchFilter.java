package com.darkness.tasks.tasks;

import com.darkness.tasks.utils.TaskPriority;
import com.darkness.tasks.utils.TaskStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.springframework.data.domain.Pageable;

public record TaskSearchFilter(
        Long creatorId,
        Long assignedUserId,
        TaskStatus taskStatus,
        TaskPriority taskPriority,
        Integer pageSize,
        Integer pageNumber) {

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull TaskSearchFilter of(
            @Range(from = 0, to = Long.MAX_VALUE) Long creatorId,
            @Range(from = 0, to = Long.MAX_VALUE) Long assignedUserId,
            TaskStatus taskStatus,
            TaskPriority taskPriority,
            @Range(from = 0, to = Integer.MAX_VALUE) Integer pageSize,
            @Range(from = 0, to = Integer.MAX_VALUE) Integer pageNumber){

        return new TaskSearchFilter(creatorId, assignedUserId, taskStatus, taskPriority, pageSize, pageNumber);
    }

    public @NotNull Pageable getPageable(int defaultPageSize){
        int pageSize = pageSize() == null ? defaultPageSize : pageSize();
        int pageNumber = pageNumber() == null ? 0 : pageNumber();

        return Pageable.ofSize(pageSize).withPage(pageNumber);
    }
}
