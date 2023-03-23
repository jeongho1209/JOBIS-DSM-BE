package team.returm.jobis.domain.company.domain.repository;

import com.querydsl.jpa.JPAExpressions;
import team.returm.jobis.domain.company.domain.repository.vo.QStudentQueryCompaniesVO;
import team.returm.jobis.domain.company.domain.repository.vo.StudentQueryCompaniesVO;
import team.returm.jobis.domain.company.domain.Company;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import team.returm.jobis.domain.company.presentation.dto.response.QQueryCompanyDetailsResponse;
import team.returm.jobis.domain.company.presentation.dto.response.QueryCompanyDetailsResponse;
import team.returm.jobis.domain.recruitment.domain.enums.RecruitStatus;

import static team.returm.jobis.domain.company.domain.QCompany.company;
import static team.returm.jobis.domain.recruitment.domain.QRecruitment.recruitment;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final CompanyJpaRepository companyJpaRepository;
    private final JPAQueryFactory queryFactory;

    public List<StudentQueryCompaniesVO> queryCompanyVoList() {
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
                .orderBy(company.name.desc())
                .fetch();
    }

    public QueryCompanyDetailsResponse queryCompanyDetails(UUID companyId) {
        return queryFactory
                .select(
                        new QQueryCompanyDetailsResponse(
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
                        recruitment.createdAt.eq(
                                JPAExpressions.select(recruitment.createdAt.max())
                                        .from(recruitment)
                                        .where(
                                                recruitment.company.id.eq(company.id),
                                                recruitment.status.eq(RecruitStatus.RECRUITING)
                                        )
                        )
                )
                .where(company.id.eq(companyId))
                .fetchOne();
    }

    public Optional<Company> queryCompanyById(UUID companyId) {
        return companyJpaRepository.findById(companyId);
    }

    public boolean existsCompanyByBizNo(String bizNo) {
        return companyJpaRepository.existsByBizNo(bizNo);
    }

    public void saveCompany(Company company) {
        companyJpaRepository.save(company);
    }
}