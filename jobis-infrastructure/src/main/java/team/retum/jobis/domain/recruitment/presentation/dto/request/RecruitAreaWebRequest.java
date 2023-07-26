package team.retum.jobis.domain.recruitment.presentation.dto.request;

import com.example.jobisapplication.domain.recruitment.dto.request.CreateRecruitAreaRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class RecruitAreaWebRequest {

    @NotNull
    private List<Long> jobCodes;

    @NotNull
    private List<Long> techCodes;

    @NotNull
    private int hiring;

    @NotBlank
    private String majorTask;

    public CreateRecruitAreaRequest toDomainRequest() {
        return CreateRecruitAreaRequest.builder()
                .hiring(this.hiring)
                .jobCodes(this.jobCodes)
                .techCodes(this.techCodes)
                .majorTask(this.majorTask)
                .build();
    }


}