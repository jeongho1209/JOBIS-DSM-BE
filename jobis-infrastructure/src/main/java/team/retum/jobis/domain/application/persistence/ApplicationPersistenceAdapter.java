package team.retum.jobis.domain.application.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.retum.jobis.domain.acceptance.persistence.repository.vo.QQueryApplicationDetailVO;
import team.retum.jobis.domain.application.model.Application;
import team.retum.jobis.domain.application.model.ApplicationAttachment;
import team.retum.jobis.domain.application.model.ApplicationStatus;
import team.retum.jobis.domain.application.persistence.mapper.ApplicationAttachmentMapper;
import team.retum.jobis.domain.application.persistence.mapper.ApplicationMapper;
import team.retum.jobis.domain.application.persistence.repository.ApplicationAttachmentJpaRepository;
import team.retum.jobis.domain.application.persistence.repository.ApplicationJpaRepository;
import team.retum.jobis.domain.application.persistence.repository.vo.QQueryApplicationVO;
import team.retum.jobis.domain.application.persistence.repository.vo.QQueryFieldTraineesVO;
import team.retum.jobis.domain.application.persistence.repository.vo.QQueryPassedApplicationStudentsVO;
import team.retum.jobis.domain.application.persistence.repository.vo.QQueryTotalApplicationCountVO;
import team.retum.jobis.domain.application.persistence.repository.vo.QueryTotalApplicationCountVO;
import team.retum.jobis.domain.application.spi.ApplicationPort;
import team.retum.jobis.domain.application.spi.vo.ApplicationDetailVO;
import team.retum.jobis.domain.application.spi.vo.ApplicationVO;
import team.retum.jobis.domain.application.spi.vo.FieldTraineesVO;
import team.retum.jobis.domain.application.spi.vo.PassedApplicationStudentsVO;
import team.retum.jobis.domain.student.persistence.entity.QStudentEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static team.retum.jobis.domain.application.persistence.entity.QApplicationAttachmentEntity.applicationAttachmentEntity;
import static team.retum.jobis.domain.application.persistence.entity.QApplicationEntity.applicationEntity;
import static team.retum.jobis.domain.company.persistence.entity.QCompanyEntity.companyEntity;
import static team.retum.jobis.domain.recruitment.persistence.entity.QRecruitmentEntity.recruitmentEntity;
import static team.retum.jobis.domain.student.persistence.entity.QStudentEntity.studentEntity;

@RequiredArgsConstructor
@Repository
public class ApplicationPersistenceAdapter implements ApplicationPort {

    private final ApplicationJpaRepository applicationJpaRepository;
    private final ApplicationAttachmentJpaRepository applicationAttachmentJpaRepository;
    private final ApplicationMapper applicationMapper;
    private final ApplicationAttachmentMapper applicationAttachmentMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ApplicationVO> queryApplicationByConditions(Long recruitmentId, Long studentId, ApplicationStatus applicationStatus, String studentName) {
        return queryFactory
                .selectFrom(applicationEntity)
                .join(applicationEntity.student, studentEntity)
                .join(applicationEntity.recruitment, recruitmentEntity)
                .leftJoin(applicationEntity.applicationAttachments, applicationAttachmentEntity)
                .leftJoin(recruitmentEntity.company, companyEntity)
                .where(
                        eqRecruitmentId(recruitmentId),
                        eqStudentId(studentId),
                        eqApplicationStatus(applicationStatus),
                        containStudentName(studentName)
                )
                .orderBy(applicationEntity.createdAt.desc())
                .transform(
                        groupBy(applicationEntity.id)
                                .list(
                                        new QQueryApplicationVO(
                                                applicationEntity.id,
                                                studentEntity.name,
                                                studentEntity.grade,
                                                studentEntity.number,
                                                studentEntity.classRoom,
                                                studentEntity.profileImageUrl,
                                                companyEntity.name,
                                                list(applicationAttachmentEntity),
                                                applicationEntity.createdAt,
                                                applicationEntity.applicationStatus
                                        )
                                )
                ).stream()
                .map(application -> ApplicationVO.builder()
                        .id(application.getId())
                        .name(application.getName())
                        .grade(application.getGrade())
                        .number(application.getNumber())
                        .classNumber(application.getClassNumber())
                        .profileImageUrl(application.getProfileImageUrl())
                        .companyName(application.getCompanyName())
                        .applicationAttachmentEntities(
                                application.getApplicationAttachmentEntities().stream()
                                        .map(applicationAttachmentMapper::toDomain)
                                        .toList()
                        )
                        .createdAt(application.getCreatedAt())
                        .applicationStatus(application.getApplicationStatus())
                        .build()
                )
                .toList();
    }

    @Override
    public Long queryApplicationCountByCondition(ApplicationStatus applicationStatus, String studentName) {
        return queryFactory
                .select(applicationEntity.count())
                .from(applicationEntity)
                .where(
                        eqApplicationStatus(applicationStatus),
                        containStudentName(studentName)
                ).fetchOne();
    }

    @Override
    public List<FieldTraineesVO> queryApplicationsFieldTraineesByCompanyId(Long companyId) {
        return queryFactory
                .select(
                        new QQueryFieldTraineesVO(
                                applicationEntity.id,
                                studentEntity.grade,
                                studentEntity.classRoom,
                                studentEntity.number,
                                studentEntity.name,
                                applicationEntity.startDate,
                                applicationEntity.endDate
                        )
                )
                .from(applicationEntity)
                .join(applicationEntity.student, studentEntity)
                .on(applicationEntity.student.id.eq(studentEntity.id))
                .join(applicationEntity.recruitment, recruitmentEntity)
                .on(recentRecruitment(companyId))
                .where(applicationEntity.applicationStatus.eq(ApplicationStatus.FIELD_TRAIN))
                .fetch().stream()
                .map(FieldTraineesVO.class::cast)
                .toList();
    }

    @Override
    public List<PassedApplicationStudentsVO> queryPassedApplicationStudentsByCompanyId(Long companyId) {
        return queryFactory
                .select(
                        new QQueryPassedApplicationStudentsVO(
                                applicationEntity.id,
                                studentEntity.name,
                                studentEntity.grade,
                                studentEntity.classRoom,
                                studentEntity.number
                        )
                )
                .from(applicationEntity)
                .join(applicationEntity.student, studentEntity)
                .join(applicationEntity.recruitment, recruitmentEntity)
                .join(recruitmentEntity.company, companyEntity)
                .where(
                        companyEntity.id.eq(companyId),
                        applicationEntity.applicationStatus.eq(ApplicationStatus.PASS)
                )
                .fetch().stream()
                .map(PassedApplicationStudentsVO.class::cast)
                .toList();
    }

    @Override
    public QueryTotalApplicationCountVO queryTotalApplicationCount() {
        QStudentEntity approvedStudent = new QStudentEntity("approvedStudent");
        QStudentEntity passedStudent = new QStudentEntity("passedStudent");
        return queryFactory
                .select(
                        new QQueryTotalApplicationCountVO(
                                studentEntity.count(),
                                passedStudent.countDistinct(),
                                approvedStudent.countDistinct()
                        )
                )
                .from(applicationEntity)
                .leftJoin(applicationEntity.student, approvedStudent)
                .on(approvedStudent.applications.any().applicationStatus.eq(ApplicationStatus.APPROVED))
                .leftJoin(applicationEntity.student, passedStudent)
                .on(
                        passedStudent.applications.any().applicationStatus.eq(ApplicationStatus.PASS)
                                .or(passedStudent.applications.any().applicationStatus.eq(ApplicationStatus.FIELD_TRAIN))
                )
                .rightJoin(applicationEntity.student, studentEntity)
                .fetchOne();
    }

    @Override
    public Application saveApplication(Application application) {
        return applicationMapper.toDomain(
                applicationJpaRepository.save(applicationMapper.toEntity(application))
        );
    }

    @Override
    public void deleteApplicationByIds(List<Long> applicationIds) {
        applicationJpaRepository.deleteByIdIn(applicationIds);
    }

    @Override
    public void saveAllApplicationAttachment(List<ApplicationAttachment> applicationAttachments) {
        applicationAttachmentJpaRepository.saveAll(
                applicationAttachments.stream()
                        .map(applicationAttachmentMapper::toEntity)
                        .toList()
        );
    }

    @Override
    public List<Application> queryApplicationsByIds(List<Long> applicationIds) {
        return applicationJpaRepository.findAllByIdIn(applicationIds).stream()
                .map(applicationMapper::toDomain)
                .toList();
    }

    @Override
    public List<ApplicationDetailVO> queryApplicationDetailsByIds(List<Long> applicationIds) {
        return queryFactory
                .select(
                        new QQueryApplicationDetailVO(
                                applicationEntity.id,
                                studentEntity.name,
                                studentEntity.grade,
                                studentEntity.classRoom,
                                studentEntity.number,
                                companyEntity.id,
                                companyEntity.businessArea,
                                applicationEntity.applicationStatus
                        )
                )
                .from(applicationEntity)
                .join(applicationEntity.student, studentEntity)
                .join(applicationEntity.recruitment, recruitmentEntity)
                .join(recruitmentEntity.company, companyEntity)
                .where(applicationEntity.id.in(applicationIds))
                .fetch().stream()
                .map(ApplicationDetailVO.class::cast)
                .toList();
    }

    @Override
    public Optional<Application> queryApplicationById(Long applicationId) {
        return applicationJpaRepository.findById(applicationId)
                .map(applicationMapper::toDomain);
    }

    @Override
    public void deleteApplication(Application application) {
        applicationJpaRepository.delete(
                applicationMapper.toEntity(application)
        );
    }

    @Override
    public void changeApplicationStatus(ApplicationStatus status, List<Long> applicationIds) {
        queryFactory
                .update(applicationEntity)
                .set(applicationEntity.applicationStatus, status)
                .where(applicationEntity.id.in(applicationIds))
                .execute();
    }

    @Override
    public void updateFieldTrainDate(LocalDate startDate, LocalDate endDate, List<Long> applicationIds) {
        queryFactory
                .update(applicationEntity)
                .set(applicationEntity.startDate, startDate)
                .set(applicationEntity.endDate, endDate)
                .where(applicationEntity.id.in(applicationIds))
                .execute();
    }

    @Override
    public boolean existsApplicationByStudentIdAndApplicationStatusIn(
            Long studentId,
            List<ApplicationStatus> applicationStatuses
    ) {
        return applicationJpaRepository.existsByStudentIdAndApplicationStatusIn(studentId, applicationStatuses);
    }

    @Override
    public void saveAllApplications(List<Application> applications) {
        applicationJpaRepository.saveAll(
                applications.stream()
                        .map(applicationMapper::toEntity)
                        .toList()
        );
    }

    //==conditions==//

    private BooleanExpression eqRecruitmentId(Long recruitmentId) {
        return recruitmentId == null ? null : recruitmentEntity.id.eq(recruitmentId);
    }

    private BooleanExpression eqStudentId(Long studentId) {
        return studentId == null ? null : studentEntity.id.eq(studentId);
    }

    private BooleanExpression eqApplicationStatus(ApplicationStatus status) {
        return status == null ? null : applicationEntity.applicationStatus.eq(status);
    }

    private BooleanExpression containStudentName(String studentName) {
        return studentName == null ? null : studentEntity.name.contains(studentName);
    }

    private BooleanExpression recentRecruitment(Long companyId) {
        return recruitmentEntity.createdAt.eq(
                select(recruitmentEntity.createdAt.max())
                        .from(recruitmentEntity)
                        .where(recruitmentEntity.company.id.eq(companyId))
        );
    }
}
