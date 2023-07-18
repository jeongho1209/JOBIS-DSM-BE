package team.retum.jobis.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.retum.jobis.domain.user.persistence.User;
import team.retum.jobis.domain.user.exception.InvalidPasswordException;
import team.retum.jobis.domain.user.facade.UserFacade;
import team.retum.jobis.domain.user.presentation.dto.request.LoginRequest;
import team.retum.jobis.domain.user.presentation.dto.response.TokenResponse;
import com.example.jobisapplication.common.annotation.Service;
import team.retum.jobis.global.security.jwt.JwtTokenAdapter;
import team.retum.jobis.global.security.jwt.TokenType;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenAdapter jwtTokenAdapter;

    public TokenResponse execute(LoginRequest request) {

        User user = userFacade.getUser(request.getAccountId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw InvalidPasswordException.EXCEPTION;
        }

        String accessToken = jwtTokenAdapter.generateAccessToken(user.getId(), user.getAuthority());
        String refreshToken = jwtTokenAdapter.generateRefreshToken(user.getId(), user.getAuthority());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessExpiresAt(jwtTokenAdapter.getExpiredAt(TokenType.ACCESS))
                .refreshToken(refreshToken)
                .refreshExpiresAt(jwtTokenAdapter.getExpiredAt(TokenType.REFRESH))
                .authority(user.getAuthority())
                .build();
    }
}
