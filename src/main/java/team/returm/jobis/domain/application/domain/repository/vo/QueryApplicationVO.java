package team.returm.jobis.domain.application.domain.repository.vo;

import team.returm.jobis.domain.application.domain.enums.ApplicationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class QueryApplicationVO {

    private final UUID id;
    private final String name;
    private final Integer grade;
    private final Integer number;
    private final Integer classNumber;
    private final String companyName;
    private final List<String> applicationAttachmentUrl;
    private final LocalDateTime createdAt;
    private final ApplicationStatus applicationStatus;

    @QueryProjection
    public QueryApplicationVO(UUID id, String name, Integer grade, Integer number,
                              Integer classNumber, String companyName, List<String> applicationAttachmentUrl,
                              LocalDateTime createdAt, ApplicationStatus applicationStatus) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.number = number;
        this.classNumber = classNumber;
        this.companyName = companyName;
        this.applicationAttachmentUrl = applicationAttachmentUrl;
        this.createdAt = createdAt;
        this.applicationStatus = applicationStatus;
    }
}