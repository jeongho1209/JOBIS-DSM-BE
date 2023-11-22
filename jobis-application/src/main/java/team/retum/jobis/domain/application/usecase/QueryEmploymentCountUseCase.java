package team.retum.jobis.domain.application.usecase;

import lombok.RequiredArgsConstructor;
import team.retum.jobis.common.annotation.ReadOnlyUseCase;
import team.retum.jobis.domain.application.dto.response.QueryEmploymentCountResponse;
import team.retum.jobis.domain.application.model.ApplicationStatus;
import team.retum.jobis.domain.application.spi.QueryApplicationPort;
import team.retum.jobis.domain.student.spi.QueryStudentPort;

import java.util.List;

@RequiredArgsConstructor
@ReadOnlyUseCase
public class QueryEmploymentCountUseCase {

    private final QueryApplicationPort queryApplicationPort;
    private final QueryStudentPort queryStudentPort;

    public QueryEmploymentCountResponse execute() {
        int totalStudentCount = queryStudentPort.queryStudentCountByGrade(3);
        int approvedApplicationCount = queryApplicationPort.queryApplicationCountByStatusIn(List.of(ApplicationStatus.APPROVED));
        int passedApplicationCount = queryApplicationPort.queryApplicationCountByStatusIn(List.of(ApplicationStatus.PASS, ApplicationStatus.FIELD_TRAIN));

        return QueryEmploymentCountResponse.builder()
                .totalStudentCount(totalStudentCount)
                .approvedCount(approvedApplicationCount)
                .passedCount(passedApplicationCount)
                .build();
    }
}
