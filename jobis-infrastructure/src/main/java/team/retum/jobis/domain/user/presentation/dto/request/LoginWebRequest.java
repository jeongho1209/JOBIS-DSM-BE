package team.retum.jobis.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.retum.jobis.domain.user.dto.LoginRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class LoginWebRequest {

    @Size(max = 30)
    @NotBlank
    private String accountId;

    @NotBlank
    private String password;

    public LoginRequest toDomainRequest() {
        return LoginRequest.builder()
                .accountId(this.accountId)
                .password(this.password)
                .build();
    }
}