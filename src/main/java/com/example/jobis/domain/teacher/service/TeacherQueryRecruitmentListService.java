package com.example.jobis.domain.teacher.service;

import com.example.jobis.domain.recruitment.domain.enums.RecruitStatus;
import com.example.jobis.domain.recruitment.domain.repository.RecruitmentRepository;
import com.example.jobis.domain.teacher.presentaion.dto.response.TQueryRecruitmentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherQueryRecruitmentListService {
    private final RecruitmentRepository recruitmentRepository;

    @Transactional(readOnly = true)
    public List<TQueryRecruitmentListResponse> execute(String companyName, LocalDate start, LocalDate end,
                                                       Integer year, RecruitStatus status, Integer page) {
        return recruitmentRepository.queryRecruitmentsByConditions(year, start, end, status, companyName, page-1).stream()
                .map(
                        r -> TQueryRecruitmentListResponse.builder()
                                .id(r.getRecruitment().getId())
                                .recruitmentStatus(r.getRecruitment().getStatus())
                                .companyName(r.getCompany().getName())
                                .companyType(r.getCompany().getType())
                                .start(r.getRecruitment().getRecruitDate().getStartDate())
                                .end(r.getRecruitment().getRecruitDate().getFinishDate())
                                .militarySupport(r.getRecruitment().isMilitarySupport())
                                .applicationCount(r.getRecruitment().getApplicationCount())
                                .recruitmentCount(r.getTotalHiring())
                                .recruitmentJob(r.getRecruitAreaList())
                                .build()
                ).toList();
    }
}
