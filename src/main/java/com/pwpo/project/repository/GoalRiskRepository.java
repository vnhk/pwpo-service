package com.pwpo.project.repository;

import com.pwpo.project.model.GoalRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GoalRiskRepository extends JpaRepository<GoalRisk, Long> {
}
