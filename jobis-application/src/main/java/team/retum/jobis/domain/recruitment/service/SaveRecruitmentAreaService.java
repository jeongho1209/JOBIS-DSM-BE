package team.retum.jobis.domain.recruitment.service;

import lombok.RequiredArgsConstructor;
import team.retum.jobis.common.annotation.Service;
import team.retum.jobis.common.util.StringUtil;
import team.retum.jobis.domain.code.model.Code;
import team.retum.jobis.domain.code.model.RecruitAreaCode;
import team.retum.jobis.domain.code.spi.QueryCodePort;
import team.retum.jobis.domain.recruitment.dto.request.CreateRecruitAreaRequest;
import team.retum.jobis.domain.recruitment.model.RecruitArea;
import team.retum.jobis.domain.recruitment.spi.CommandRecruitmentPort;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveRecruitmentAreaService {

    private final CommandRecruitmentPort commandRecruitmentPort;
    private final QueryCodePort queryCodePort;

    public void execute(CreateRecruitAreaRequest request, Long recruitmentId) {
        List<Code> jobCodes = queryCodePort.queryCodesByIdIn(request.getJobCodes());
        String recruitJobs = StringUtil.joinStringList(jobCodes.stream().map(Code::getKeyword).toList());

        RecruitArea savedRecruitArea = commandRecruitmentPort.saveRecruitmentArea(
                RecruitArea.builder()
                        .recruitmentId(recruitmentId)
                        .hiredCount(request.getHiring())
                        .jobCodes(recruitJobs)
                        .majorTask(request.getMajorTask())
                        .build()
        );

        List<RecruitAreaCode> recruitAreaCodes = request.getTechCodes().stream()
                .map(
                        code -> RecruitAreaCode.builder()
                                .recruitAreaId(savedRecruitArea.getId())
                                .codeId(code)
                                .build()
                ).toList();

        commandRecruitmentPort.saveAllRecruitmentAreaCodes(recruitAreaCodes);
    }
}