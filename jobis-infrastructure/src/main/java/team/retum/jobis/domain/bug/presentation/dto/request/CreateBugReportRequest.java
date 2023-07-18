package team.retum.jobis.domain.bug.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.jobisapplication.domain.bug.model.DevelopmentArea;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateBugReportRequest {

    @NotBlank
    @Size(min = 1, max = 20)
    private String title;

    @NotBlank
    @Size(min = 1, max = 400)
    private String content;

    @NotNull
    private DevelopmentArea developmentArea;

    private List<String> attachmentUrls;
}
