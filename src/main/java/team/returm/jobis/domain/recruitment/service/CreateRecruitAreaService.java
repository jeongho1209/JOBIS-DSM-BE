package team.returm.jobis.domain.recruitment.service;

import lombok.RequiredArgsConstructor;
import team.returm.jobis.domain.code.domain.Code;
import team.returm.jobis.domain.code.facade.CodeFacade;
import team.returm.jobis.domain.recruitment.domain.RecruitArea;
import team.returm.jobis.domain.recruitment.domain.Recruitment;
import team.returm.jobis.domain.recruitment.domain.repository.RecruitAreaJpaRepository;
import team.returm.jobis.domain.recruitment.domain.repository.RecruitmentRepository;
import team.returm.jobis.domain.recruitment.facade.RecruitFacade;
import team.returm.jobis.domain.recruitment.presentation.dto.request.CreateRecruitAreaRequest;
import team.returm.jobis.domain.user.domain.User;
import team.returm.jobis.domain.user.domain.enums.Authority;
import team.returm.jobis.domain.user.facade.UserFacade;
import team.returm.jobis.global.annotation.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class CreateRecruitAreaService {

    private final RecruitAreaJpaRepository recruitAreaJpaRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final CodeFacade codeFacade;
    private final RecruitFacade recruitFacade;
    private final UserFacade userFacade;

    public void execute(CreateRecruitAreaRequest request, Long recruitmentId) {
        User user = userFacade.getCurrentUser();

        Recruitment recruitment = recruitFacade.queryRecruitmentById(recruitmentId);
        if (user.getAuthority() == Authority.COMPANY) {
            recruitment.checkCompany(user.getId());
        }

        RecruitArea recruitArea = recruitAreaJpaRepository.save(
                RecruitArea.builder()
                        .majorTask(request.getMajorTask())
                        .hiredCount(request.getHiring())
                        .recruitment(recruitment)
                        .build()
        );

        List<Code> codes = codeFacade.queryCodesByIdIn(
                Stream.of(request.getJobCodes(), request.getTechCodes())
                        .flatMap(Collection::stream)
                        .toList()
        );

        recruitmentRepository.saveAllRecruitAreaCodes(
                codeFacade.generateRecruitAreaCode(recruitArea, codes)
        );
    }
}
