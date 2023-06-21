package team.returm.jobis.domain.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.returm.jobis.domain.auth.domain.types.AuthCodeType;
import team.returm.jobis.global.util.RegexProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SendAuthCodeRequest {

    @NotBlank
    @Pattern(regexp = RegexProperty.EMAIL)
    private String email;

    @NotNull
    private AuthCodeType authCodeType;
}
