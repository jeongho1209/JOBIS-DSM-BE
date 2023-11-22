package team.retum.jobis.domain.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.retum.jobis.domain.application.spi.vo.TotalApplicationCountVO;

@Getter
@Builder
public class QueryEmploymentCountResponse {

    private final int totalStudentCount;
    private final int passedCount;
    private final int approvedCount;

}
