package team.returm.jobis.domain.recruitment.service;

import team.returm.jobis.domain.recruitment.domain.Recruitment;
import team.returm.jobis.domain.recruitment.domain.repository.RecruitmentRepository;
import team.returm.jobis.domain.recruitment.exception.RecruitmentNotFoundException;
import team.returm.jobis.domain.recruitment.facade.RecruitFacade;
import team.returm.jobis.domain.recruitment.presentation.dto.response.StudentRecruitDetailsResponse;
import team.returm.jobis.global.annotation.ReadOnlyService;
import team.returm.jobis.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
@ReadOnlyService
public class StudentQueryRecruitmentDetailService {
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitFacade recruitFacade;

    public StudentRecruitDetailsResponse execute(UUID recruitId) {
        Recruitment recruitment = recruitmentRepository.queryRecruitmentById(recruitId)
                .orElseThrow(() -> RecruitmentNotFoundException.EXCEPTION);

        return StudentRecruitDetailsResponse.builder()
                .companyId(recruitment.getCompany().getId())
                .areas(recruitFacade.queryRecruitAreas(recruitment.getId()))
                .pay(recruitment.getPayInfo().getPay())
                .trainPay(recruitment.getPayInfo().getTrainingPay())
                .etc(recruitment.getEtc())
                .requiredGrade(recruitment.getRequiredGrade())
                .requiredLicenses(StringUtil.divideString(recruitment.getRequiredLicenses()))
                .startDate(recruitment.getRecruitDate().getStartDate())
                .endDate(recruitment.getRecruitDate().getFinishDate())
                .hiringProgress(StringUtil.divideString(recruitment.getHiringProgress()))
                .workHours(recruitment.getWorkingHours())
                .preferentialTreatment(recruitment.getPreferentialTreatment())
                .military(recruitment.getMilitarySupport())
                .benefits(recruitment.getBenefits())
                .build();
    }
}
