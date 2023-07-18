package team.retum.jobis.domain.recruitment.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.retum.jobis.domain.code.persistence.CodeEntity;
import team.retum.jobis.domain.code.persistence.RecruitAreaCodeEntity;
import com.example.jobisapplication.domain.code.domain.CodeType;
import team.retum.jobis.domain.recruitment.persistence.RecruitArea;
import team.retum.jobis.domain.recruitment.persistence.Recruitment;
import team.retum.jobis.domain.recruitment.persistence.repository.RecruitmentRepository;
import team.retum.jobis.domain.recruitment.exception.RecruitmentNotFoundException;
import com.example.jobisapplication.common.util.StringUtil;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RecruitmentFacade {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment queryRecruitmentById(Long id) {
        return recruitmentRepository.queryRecruitmentById(id)
                .orElseThrow(() -> RecruitmentNotFoundException.EXCEPTION);
    }

    public void createRecruitArea(
            Map<CodeType, List<CodeEntity>> codes,
            Recruitment recruitment,
            String majorTask,
            int hiredCount
    ) {
        List<String> jobCodes = codes.get(CodeType.JOB).stream()
                .map(CodeEntity::getKeyword)
                .toList();

        RecruitArea recruitArea = recruitmentRepository.saveRecruitArea(
                RecruitArea.builder()
                        .majorTask(majorTask)
                        .hiredCount(hiredCount)
                        .recruitment(recruitment)
                        .jobCodes(StringUtil.joinStringList(jobCodes))
                        .build()
        );

        recruitmentRepository.saveAllRecruitAreaCodes(
                codes.get(CodeType.TECH).stream()
                        .map(code -> new RecruitAreaCodeEntity(recruitArea, code))
                        .toList()
        );
    }
}
