package team.retum.jobis.domain.auth.service;

import lombok.RequiredArgsConstructor;
import team.retum.jobis.domain.auth.domain.AuthCode;
import team.retum.jobis.domain.auth.domain.repository.AuthCodeRepository;
import team.retum.jobis.domain.auth.domain.types.AuthCodeType;
import team.retum.jobis.domain.auth.presentation.dto.request.SendAuthCodeRequest;
import team.retum.jobis.domain.student.exception.StudentAlreadyExistsException;
import team.retum.jobis.domain.student.exception.StudentNotFoundException;
import team.retum.jobis.domain.user.domain.repository.UserRepository;
import team.retum.jobis.global.annotation.Service;
import team.retum.jobis.global.util.StringUtil;
import team.retum.jobis.infrastructure.ses.SesUtil;

@RequiredArgsConstructor
@Service
public class SendAuthCodeService {

    private final AuthCodeRepository authCodeRepository;
    private final UserRepository userRepository;
    private final SesUtil sesUtil;

    public void execute(SendAuthCodeRequest request) {
        if (request.getAuthCodeType() == AuthCodeType.SIGN_UP) {
            if (userRepository.existsByAccountId(request.getEmail())) {
                throw StudentAlreadyExistsException.EXCEPTION;
            }
        } else {
            if (!userRepository.existsByAccountId(request.getEmail())) {
                throw StudentNotFoundException.EXCEPTION;
            }
        }

        AuthCode authCode = AuthCode.builder()
                .code(StringUtil.generateRandomCode(6))
                .ttl(300)
                .isVerified(false)
                .email(request.getEmail())
                .build();
        authCodeRepository.save(authCode);

        sesUtil.sendMail(authCode.getCode(), authCode.getEmail());
    }
}