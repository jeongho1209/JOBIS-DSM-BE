package team.returm.jobis.domain.company.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.returm.jobis.domain.application.domain.enums.ApplicationStatus;
import team.returm.jobis.domain.company.domain.Company;
import team.returm.jobis.domain.company.domain.enums.CompanyType;
import team.returm.jobis.domain.company.domain.repository.vo.QQueryCompanyDetailsVO;
import team.returm.jobis.domain.company.domain.repository.vo.QStudentQueryCompaniesVO;
import team.returm.jobis.domain.company.domain.repository.vo.QTeacherQueryCompaniesVO;
import team.returm.jobis.domain.company.domain.repository.vo.QTeacherQueryEmployCompaniesVO;
import team.returm.jobis.domain.company.domain.repository.vo.QueryCompanyDetailsVO;
import team.returm.jobis.domain.company.domain.repository.vo.StudentQueryCompaniesVO;
import team.returm.jobis.domain.company.domain.repository.vo.TeacherQueryCompaniesVO;
import team.returm.jobis.domain.company.domain.repository.vo.TeacherQueryEmployCompaniesVO;
import team.returm.jobis.domain.recruitment.domain.enums.RecruitStatus;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static team.returm.jobis.domain.acceptance.domain.QAcceptance.acceptance;
import static team.returm.jobis.domain.application.domain.QApplication.application;
import static team.returm.jobis.domain.company.domain.QCompany.company;
import static team.returm.jobis.domain.company.domain.QCompanyAttachment.companyAttachment;
import static team.returm.jobis.domain.recruitment.domain.QRecruitment.recruitment;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final CompanyJpaRepository companyJpaRepository;
    private final JPAQueryFactory queryFactory;

    public List<StudentQueryCompaniesVO> queryCompanyVoList(Integer page, String name) {
        long pageSize = 11;
        return queryFactory
                .select(
                        new QStudentQueryCompaniesVO(
                                company.id,
                                company.name,
                                company.companyLogoUrl,
                                company.sales
                        )
                )
                .from(company)
                .where(containsName(name))
                .orderBy(company.name.desc())
                .offset(page * pageSize)
                .limit(pageSize)
                .fetch();
    }

    public List<TeacherQueryCompaniesVO> queryCompaniesByConditions(CompanyType type, String companyName, String region, String businessArea, Long page) {
        long pageSize = 11;
        return queryFactory
                .selectFrom(company)
                .leftJoin(company.acceptances, acceptance)
                .leftJoin(recruitment)
                .on(recentRecruitment(null))
                .where(
                        eqCompanyType(type),
                        containsName(companyName),
                        eqRegion(region),
                        eqBusinessArea(businessArea)
                )
                .orderBy(company.name.desc())
                .groupBy(
                        company.id,
                        company.name,
                        company.address.mainAddress,
                        company.businessArea,
                        company.workersCount,
                        company.sales,
                        company.type,
                        recruitment.recruitYear
                )
                .offset(page * pageSize)
                .limit(pageSize)
                .transform(
                        groupBy(company.id)
                                .list(
                                        new QTeacherQueryCompaniesVO(
                                                company.id,
                                                company.name,
                                                company.address.mainAddress,
                                                company.businessArea,
                                                company.workersCount,
                                                company.sales,
                                                company.type,
                                                company.convention,
                                                recruitment.personalContact,
                                                recruitment.recruitYear,
                                                acceptance.count()
                                                //TODO :: 후기 개수 반환
                                        )
                                )
                );
    }

    public QueryCompanyDetailsVO queryCompanyDetails(Long companyId) {
        return queryFactory
                .select(
                        new QQueryCompanyDetailsVO(
                                company.bizNo,
                                company.companyLogoUrl,
                                company.companyIntroduce,
                                company.address.mainZipCode,
                                company.address.mainAddress,
                                company.address.subZipCode,
                                company.address.subAddress,
                                company.manager.managerName,
                                company.manager.managerPhoneNo,
                                company.manager.subManagerName,
                                company.manager.subManagerPhoneNo,
                                company.fax,
                                company.email,
                                company.representative,
                                company.foundedAt,
                                company.workersCount,
                                company.sales,
                                recruitment.id
                        )
                )
                .from(company)
                .leftJoin(recruitment)
                .on(
                        recruitment.company.id.eq(company.id),
                        recentRecruitment(RecruitStatus.RECRUITING)
                )
                .where(company.id.eq(companyId))
                .fetchOne();
    }

    public List<TeacherQueryEmployCompaniesVO> queryEmployCompanies(String name, CompanyType type, Integer year) {
        return queryFactory
                .select(
                        new QTeacherQueryEmployCompaniesVO(
                                company.id,
                                company.name,
                                application.count(),
                                acceptance.count()
                        )
                )
                .from(company)
                .leftJoin(company.acceptances, acceptance)
                .leftJoin(company.recruitmentList, recruitment)
                .on(
                        recruitment.company.id.eq(company.id),
                        recentRecruitment(null)
                )
                .leftJoin(recruitment.applications, application)
                .on(application.applicationStatus.eq(ApplicationStatus.FIELD_TRAIN))
                .where(
                        containsName(name),
                        eqCompanyType(type),
                        eqYear(year)
                )
                .orderBy(company.name.asc())
                .groupBy(company.id, company.name)
                .fetch();
    }

    public List<String> queryCompanyAttachmentUrls(Long companyId) {
        return queryFactory
                .select(companyAttachment.attachmentUrl)
                .from(companyAttachment)
                .where(companyAttachment.company.id.eq(companyId))
                .fetch();
    }

    public Optional<Company> queryCompanyById(Long companyId) {
        return companyJpaRepository.findById(companyId);
    }

    public boolean existsCompanyByBizNo(String bizNo) {
        return companyJpaRepository.existsByBizNo(bizNo);
    }

    public void saveCompany(Company company) {
        companyJpaRepository.save(company);
    }

    public void saveAllCompanies(List<Company> companies) {
        companyJpaRepository.saveAll(companies);
    }

    public List<Company> queryCompaniesByIdIn(List<Long> companyIds) {
        return companyJpaRepository.findAllByIdIn(companyIds);
    }

    //==conditions==//

    private BooleanExpression containsName(String name) {
        return name == null ? null : company.name.contains(name);
    }

    private BooleanExpression eqCompanyType(CompanyType type) {
        return type == null ? null : company.type.eq(type);
    }

    private BooleanExpression eqYear(Integer year) {
        return year == null ? null : recruitment.recruitYear.eq(year);
    }

    private BooleanExpression recentRecruitment(RecruitStatus status) {
        return recruitment.createdAt.eq(
                JPAExpressions.select(recruitment.createdAt.max())
                        .from(recruitment)
                        .where(
                                recruitment.company.id.eq(company.id),
                                eqRecruitmentStatus(status)
                        )
        );
    }

    private BooleanExpression eqRecruitmentStatus(RecruitStatus status) {
        return status == null ? null : recruitment.status.eq(RecruitStatus.RECRUITING);
    }


    private BooleanExpression eqRegion(String region) {
        return region == null ? null : company.address.mainAddress.startsWith(region);
    }

    private BooleanExpression eqBusinessArea(String businessArea) {
        return businessArea == null ? null : company.businessArea.eq(businessArea);
    }
}
