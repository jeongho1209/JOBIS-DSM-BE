package team.retum.jobis.domain.recruitment.dto;

import lombok.Builder;
import lombok.Getter;
import team.retum.jobis.domain.recruitment.model.RecruitStatus;

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

    private final List<Long> codes;

    private final Long studentId;

    private final String jobKeyword;

    public Long getOffset() {
        return 11L * page;
    }
}
