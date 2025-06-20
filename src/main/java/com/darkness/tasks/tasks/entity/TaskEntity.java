package com.darkness.tasks.tasks.entity;

import com.darkness.tasks.utils.TaskPriority;
import com.darkness.tasks.utils.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tasks")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "assigned_user_id", nullable = false)
    private Long assignedUserId;

    @Setter
    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "deadline_date", nullable = false)
    private LocalDate deadlineDate;

    @Setter
    @Column(name = "done_date_time")
    private LocalDateTime doneDateTime;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
}
