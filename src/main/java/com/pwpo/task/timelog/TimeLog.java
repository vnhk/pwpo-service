package com.pwpo.task.timelog;

import com.pwpo.task.Task;
import com.pwpo.user.UserDetails;
import com.pwpo.user.model.Constants;
import com.pwpo.user.model.BaseEntity;
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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    private Long id;
    private String comment;
    private Integer loggedTimeInMinutes;
    private LocalDateTime created;
    private LocalDate date;
    @ManyToOne
    private UserDetails user;
    @ManyToOne
    private Task task;
}
