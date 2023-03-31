package team.returm.jobis.domain.auth.service;

import team.returm.jobis.domain.auth.domain.AuthCode;
import team.returm.jobis.domain.auth.exception.AuthCodeNotFoundException;
import team.returm.jobis.domain.student.domain.repository.AuthCodeRepository;
import team.returm.jobis.domain.student.exception.BadAuthCodeException;
import team.returm.jobis.global.annotation.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VerifyAuthCodeService {

    private final AuthCodeRepository authCodeRepository;

    public void execute(String email, String code) {
        AuthCode authCode = authCodeRepository.findById(email)
                .orElseThrow(() -> AuthCodeNotFoundException.EXCEPTION);

        if (!authCode.getCode().equals(code)) {
            throw BadAuthCodeException.EXCEPTION;
        }

        authCodeRepository.save(
                authCode.verify()
        );
    }
}
