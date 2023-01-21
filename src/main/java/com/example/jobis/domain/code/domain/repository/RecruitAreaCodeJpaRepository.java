package com.example.jobis.domain.code.domain.repository;

import com.example.jobis.domain.code.domain.RecruitAreaCode;
import com.example.jobis.domain.code.domain.RecruitAreaCodeId;
import com.example.jobis.domain.recruit.domain.RecruitArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitAreaCodeJpaRepository extends JpaRepository<RecruitAreaCode, RecruitAreaCodeId> {
}
