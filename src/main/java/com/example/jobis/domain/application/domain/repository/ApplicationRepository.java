package com.example.jobis.domain.application.domain.repository;

import com.example.jobis.domain.application.controller.dto.response.QStudentApplicationListResponse;
import com.example.jobis.domain.application.controller.dto.response.StudentApplicationListResponse;
import com.example.jobis.domain.application.domain.Application;
import com.example.jobis.domain.application.domain.ApplicationAttachment;
import com.example.jobis.domain.company.domain.Company;
import com.example.jobis.domain.student.domain.Student;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.jobis.domain.application.domain.QApplication.application;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ApplicationRepository {

    private final ApplicationJpaRepository applicationJpaRepository;
    private final ApplicationAttachmentJpaRepository applicationAttachmentJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public List<StudentApplicationListResponse> queryStudentApplication(Student student) {
        return jpaQueryFactory
                .select(
                        new QStudentApplicationListResponse(
                                application.company.name,
                                application.applicationStatus,
                                application.createdAt
                        )
                )
                .from(application)
                .leftJoin(application.company)
                .where(application.student.eq(student))
                .orderBy(application.createdAt.desc())
                .fetch();
    }

    public Application saveApplication(Application application) {
        return applicationJpaRepository.save(application);
    }

    public List<ApplicationAttachment> saveAllApplicationAttachment(List<ApplicationAttachment> applicationAttachments) {
        return applicationAttachmentJpaRepository.saveAll(applicationAttachments);
    }

    public boolean existsApplicationByStudentAndCompany(Student student, Company company) {
        return applicationJpaRepository.existsByStudentAndCompany(student, company);
    }
}