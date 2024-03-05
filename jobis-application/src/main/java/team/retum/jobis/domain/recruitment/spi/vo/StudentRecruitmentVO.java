package team.retum.jobis.domain.recruitment.spi.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentRecruitmentVO {

    private final long recruitmentId;
    private final String companyName;
    private final int trainPay;
    private final boolean militarySupport;
    private final String companyLogoUrl;
    private final String jobCodes;
    private final String etcAreas;
    private final boolean isBookmarked;

}
