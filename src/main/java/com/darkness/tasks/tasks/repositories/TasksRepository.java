package com.darkness.tasks.tasks.repositories;

import com.darkness.tasks.tasks.entity.TaskEntity;
import com.darkness.tasks.utils.TaskPriority;
import com.darkness.tasks.utils.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TasksRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByAssignedUserIdAndTaskStatus(Long assignedUserId, TaskStatus taskStatus);

    @Query("SELECT task FROM TaskEntity task WHERE " +
            "(:creatorId IS NULL OR task.creatorId = :creatorId) " +
            "AND (:assignedUserId IS NULL OR task.assignedUserId = :assignedUserId) " +
            "AND (:taskStatus IS NULL OR task.taskStatus = :taskStatus) " +
            "AND (:taskPriority IS NULL OR task.priority = :taskPriority)")
    List<TaskEntity> searchAllByFilter(
            @Param("creatorId") Long creatorId,
            @Param("assignedUserId") Long assignedUserId,
            @Param("taskStatus") TaskStatus taskStatus,
            @Param("taskPriority") TaskPriority taskPriority,
            Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEntity entity SET entity.taskStatus = :taskStatus WHERE id = :id")
    void setTaskStatus(@Param("id") Long id, @Param("taskStatus") TaskStatus newStatus);
}
