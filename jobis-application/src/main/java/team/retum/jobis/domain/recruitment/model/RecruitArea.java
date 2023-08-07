package team.retum.jobis.domain.recruitment.model;

import lombok.Builder;
import lombok.Getter;
import team.retum.jobis.common.annotation.Aggregate;

@Getter
@Builder
@Aggregate
public class RecruitArea {

    private final Long id;

    private final Integer hiredCount;

    private final String majorTask;

    private final String jobCodes;

    private final Long recruitmentId;

}