package team.retum.jobis.domain.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.retum.jobis.domain.auth.domain.AuthCode;
import team.retum.jobis.domain.auth.domain.repository.AuthCodeRepository;
import team.retum.jobis.domain.auth.exception.UnverifiedEmailException;
import team.retum.jobis.domain.student.domain.Student;
import team.retum.jobis.domain.student.domain.repository.StudentJpaRepository;
import team.retum.jobis.domain.student.domain.repository.VerifiedStudentRepository;
import team.retum.jobis.domain.student.exception.StudentAlreadyExistsException;
import team.retum.jobis.domain.student.presentation.dto.request.StudentSignUpRequest;
import team.retum.jobis.domain.user.domain.User;
import team.retum.jobis.domain.user.domain.enums.Authority;
import team.retum.jobis.domain.user.domain.repository.UserRepository;
import team.retum.jobis.domain.user.presentation.dto.response.TokenResponse;
import team.retum.jobis.global.annotation.Service;
import team.retum.jobis.global.security.jwt.JwtTokenProvider;
import team.retum.jobis.global.security.jwt.TokenType;

@RequiredArgsConstructor
@Service
public class StudentSignUpService {

    private final StudentJpaRepository studentJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthCodeRepository authCodeRepository;
    private final VerifiedStudentRepository verifiedStudentRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse execute(StudentSignUpRequest request) {

        if (userRepository.existsByAccountId(request.getEmail())) {
            throw StudentAlreadyExistsException.EXCEPTION;
        }

        AuthCode authCode = authCodeRepository.findById(request.getEmail())
                .orElseThrow(() -> UnverifiedEmailException.EXCEPTION);
        authCode.checkIsVerified();

        if (studentJpaRepository.existsByGradeAndClassRoomAndNumber(
                request.getGrade(), request.getClassRoom(), request.getNumber())
        ) {
            throw StudentAlreadyExistsException.EXCEPTION;
        }

        User user = User.builder()
                .accountId(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authority(Authority.STUDENT)
                .build();

        Student student = studentJpaRepository.save(
                Student.builder()
                        .user(user)
                        .classRoom(request.getClassRoom())
                        .number(request.getNumber())
                        .name(request.getName())
                        .gender(request.getGender())
                        .grade(request.getGrade())
                        .department(
                                Student.getDepartment(
                                        request.getGrade(),
                                        request.getClassRoom()
                                )
                        )
                        .profileImageUrl(request.getProfileImageUrl())
                        .build()
        );

        verifiedStudentRepository.deleteVerifiedStudentByGcnAndName(
                Student.processGcn(
                        student.getGrade(),
                        student.getClassRoom(),
                        student.getNumber()
                ),
                student.getName()
        );

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getAuthority());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(), user.getAuthority());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshExpiresAt(jwtTokenProvider.getExpiredAt(TokenType.REFRESH))
                .accessExpiresAt(jwtTokenProvider.getExpiredAt(TokenType.ACCESS))
                .authority(Authority.STUDENT)
                .build();
    }
}