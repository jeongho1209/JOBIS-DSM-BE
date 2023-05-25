package team.returm.jobis.domain.recruitment.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import team.returm.jobis.domain.code.domain.Code;
import team.returm.jobis.domain.recruitment.domain.enums.RecruitStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class RecruitmentFilter {

    private final Integer year;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final RecruitStatus status;

    private final String companyName;

    private final Integer page;

    private final List<Code> codes;

    private final Long studentId;

    public Long getOffset() {
        return 11L * page;
    }
}
