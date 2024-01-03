package team.retum.jobis.domain.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StudentQueryRecruitmentsResponse {

    private final List<StudentRecruitmentResponse> recruitments;

    @Getter
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Builder
    public static class StudentRecruitmentResponse {
        private long id;

        private String companyName;

        private String companyProfileUrl;

        private int trainPay;

        private boolean militarySupport;

        private String hiringJobs;

        private boolean bookmarked;
    }
}
