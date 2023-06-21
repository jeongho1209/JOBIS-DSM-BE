package team.returm.jobis.domain.application.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QueryTotalApplicationCountVO {

    private final Long totalStudentCount;
    private final Long passedCount;
    private final Long approvedCount;

    @Builder
    @QueryProjection
    public QueryTotalApplicationCountVO(Long totalStudentCount, Long passedCount, Long approvedCount) {
        this.totalStudentCount = totalStudentCount;
        this.passedCount = passedCount;
        this.approvedCount = approvedCount;
    }
}
