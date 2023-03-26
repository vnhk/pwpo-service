package com.pwpo.task.model;

import com.pwpo.common.model.Constants;
import com.pwpo.common.validator.EditProcess;
import com.pwpo.common.validator.SaveProcess;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    @Null(groups = SaveProcess.class)
    @NotNull(groups = EditProcess.class)
    private Long id;

    @Size(max = Constants.SUMMARY_MAX)
    @Column(length = Constants.SUMMARY_MAX)
    private String summary;

    @Size(min = 1, max = Constants.NAME_MAX)
    @Column(unique = true, length = Constants.NAME_MAX)
    @NotNull
    @Unique
    private String name;

    @ManyToMany
    @JoinTable(
            name = "task_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private Set<Task> tasks;
}
