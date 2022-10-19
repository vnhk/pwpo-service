package com.pwpo.user.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.project.Project;
import com.pwpo.user.ProjectRole;
import com.pwpo.user.UserDetails;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserProject extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    private Long id;
    @ManyToOne
    private UserDetails user;
    @ManyToOne
    private Project project;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private ProjectRole role;
}
