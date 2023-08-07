package team.retum.jobis.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import team.retum.jobis.common.spi.SecurityPort;
import team.retum.jobis.domain.auth.model.Authority;
import team.retum.jobis.domain.user.persistence.entity.UserEntity;
import team.retum.jobis.domain.user.persistence.repository.UserJpaRepository;
import team.retum.jobis.global.exception.InvalidTokenException;

@RequiredArgsConstructor
@Component
public class SecurityAdapter implements SecurityPort {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Authority getCurrentUserAuthority() {
        Long currentUserId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        return userJpaRepository.findById(currentUserId).map(UserEntity::getAuthority)
                .orElseThrow(() -> InvalidTokenException.EXCEPTION);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}