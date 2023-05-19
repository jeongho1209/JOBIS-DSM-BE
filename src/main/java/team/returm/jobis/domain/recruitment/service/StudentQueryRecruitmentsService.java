package team.returm.jobis.domain.recruitment.service;

import lombok.RequiredArgsConstructor;
import team.returm.jobis.domain.code.domain.Code;
import team.returm.jobis.domain.code.facade.CodeFacade;
import team.returm.jobis.domain.recruitment.domain.enums.RecruitStatus;
import team.returm.jobis.domain.recruitment.domain.repository.RecruitmentRepository;
import team.returm.jobis.domain.recruitment.presentation.dto.response.StudentQueryRecruitmentsResponse;
import team.returm.jobis.domain.recruitment.presentation.dto.response.StudentQueryRecruitmentsResponse.StudentRecruitmentResponse;
import team.returm.jobis.domain.user.facade.UserFacade;
import team.returm.jobis.global.annotation.ReadOnlyService;

import java.time.Year;
import java.util.List;

@RequiredArgsConstructor
@ReadOnlyService
public class StudentQueryRecruitmentsService {

    private final RecruitmentRepository recruitmentRepository;
    private final UserFacade userFacade;
    private final CodeFacade codeFacade;

    public StudentQueryRecruitmentsResponse execute(String name, Integer page, List<String> keywords) {
        Long studentId = userFacade.getCurrentUserId();
        List<Code> codes = codeFacade.queryCodeByKeywordIn(keywords);

        List<StudentRecruitmentResponse> recruitments =
                recruitmentRepository.queryRecruitmentsByConditions(
                                Year.now().getValue(), null, null,
                                RecruitStatus.RECRUITING, name, page - 1, codes, studentId
                        ).stream()
                        .map(
                                recruitment -> StudentRecruitmentResponse.builder()
                                        .recruitId(recruitment.getRecruitment().getId())
                                        .companyName(recruitment.getCompany().getName())
                                        .trainPay(recruitment.getRecruitment().getPayInfo().getTrainingPay())
                                        .jobCodeList(recruitment.getRecruitAreaList())
                                        .military(recruitment.getRecruitment().getMilitarySupport())
                                        .companyProfileUrl(recruitment.getCompany().getCompanyLogoUrl())
                                        .totalHiring(recruitment.getTotalHiring())
                                        .isBookmarked(recruitment.getIsBookmarked() != 0)
                                        .build()
                        ).toList();

        return new StudentQueryRecruitmentsResponse(recruitments);
    }
}
