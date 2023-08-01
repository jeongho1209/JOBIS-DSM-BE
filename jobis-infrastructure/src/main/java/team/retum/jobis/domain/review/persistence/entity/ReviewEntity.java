package team.retum.jobis.domain.review.persistence.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document("reviews")
public class ReviewEntity {

    @Id
    private String id;
    private Long companyId;
    private List<QnAElementEntity> qnAElements;
    private String studentName;
    private Integer year;
    private LocalDate createdDate;

    @Builder
    public ReviewEntity(String id, Long companyId, List<QnAElementEntity> qnAElementEntities,
                        String studentName, Integer year) {
        this.id = id;
        this.companyId = companyId;
        this.qnAElements = qnAElementEntities;
        this.studentName = studentName;
        this.year = year;
        this.createdDate = LocalDate.now();
    }
}
