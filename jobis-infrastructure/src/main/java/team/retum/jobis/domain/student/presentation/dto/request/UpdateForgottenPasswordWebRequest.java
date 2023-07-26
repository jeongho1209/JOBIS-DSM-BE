package team.retum.jobis.domain.student.presentation.dto.request;

import com.example.jobisapplication.domain.student.dto.UpdateForgottenPasswordRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.retum.jobis.global.util.RegexProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UpdateForgottenPasswordWebRequest {

    @NotBlank
    @Pattern(regexp = RegexProperty.EMAIL)
    private String email;

    @NotBlank
    @Pattern(regexp = RegexProperty.PASSWORD)
    private String password;

    public UpdateForgottenPasswordRequest toDomainRequest() {
        return UpdateForgottenPasswordRequest.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
