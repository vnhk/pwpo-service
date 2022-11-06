package com.pwpo.task.timelog;

import com.pwpo.task.model.Task;
import com.pwpo.user.UserDetails;
import com.pwpo.common.model.db.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TimeLog extends BaseEntity {
    private String comment;
    private Integer loggedTimeInMinutes;
    private LocalDateTime created;
    private LocalDate date;
    @ManyToOne
    private UserDetails user;
    @ManyToOne
    private Task task;
}
