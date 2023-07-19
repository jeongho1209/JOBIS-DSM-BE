package team.retum.jobis.domain.application.persistence.repository.vo;

import com.example.jobisapplication.domain.application.spi.vo.FieldTraineesVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class QueryFieldTraineesVO extends FieldTraineesVO {

    @QueryProjection
    public QueryFieldTraineesVO(Long applicationId, Integer grade, Integer classRoom, Integer number,
                                String studentName, LocalDate startDate, LocalDate endDate) {
        super(applicationId, grade, classRoom, number, studentName, startDate, endDate);
    }
}
