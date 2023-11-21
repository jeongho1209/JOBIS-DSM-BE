package team.retum.jobis.domain.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RecruitAreaResponse {

    private final Long id;

    private final List<String> job;

    private final List<String> tech;

    private final int hiring;

    private final String majorTask;

    private final String preferentialTreatment;
}
