package com.example.jobis.domain.recruit.domain.repository;

import com.example.jobis.domain.recruit.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruitment, Long> {
}
