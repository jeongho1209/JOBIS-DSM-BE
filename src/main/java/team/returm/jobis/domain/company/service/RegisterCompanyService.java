package team.returm.jobis.domain.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.returm.jobis.domain.code.facade.CodeFacade;
import team.returm.jobis.domain.company.domain.Company;
import team.returm.jobis.domain.company.domain.repository.CompanyRepository;
import team.returm.jobis.domain.company.exception.CompanyAlreadyExistsException;
import team.returm.jobis.domain.company.exception.CompanyNotFoundException;
import team.returm.jobis.domain.company.facade.CompanyFacade;
import team.returm.jobis.domain.company.presentation.dto.request.RegisterCompanyRequest;
import team.returm.jobis.domain.user.domain.User;
import team.returm.jobis.domain.user.domain.enums.Authority;
import team.returm.jobis.domain.user.presentation.dto.response.TokenResponse;
import team.returm.jobis.global.annotation.Service;
import team.returm.jobis.global.security.jwt.JwtTokenProvider;
import team.returm.jobis.global.security.jwt.TokenType;

@RequiredArgsConstructor
@Service
public class RegisterCompanyService {

    private final CompanyFacade companyFacade;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CodeFacade codeFacade;

    public TokenResponse execute(RegisterCompanyRequest request) {
        if (!companyFacade.checkCompany(request.getBusinessNumber())) {
            throw CompanyNotFoundException.EXCEPTION;
        }

        if (companyRepository.existsCompanyByBizNo(request.getBusinessNumber())) {
            throw CompanyAlreadyExistsException.EXCEPTION;
        }

        User user = User.builder()
                .accountId(request.getBusinessNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .authority(Authority.COMPANY)
                .build();

        String businessAreaKeyword = codeFacade.findCodeById(request.getBusinessAreaCode()).getKeyword();

        companyRepository.saveCompany(
                Company.builder()
                        .user(user)
                        .companyIntroduce(request.getCompanyIntroduce())
                        .companyLogoUrl(request.getCompanyProfileUrl())
                        .bizRegistrationUrl(request.getBizRegistrationUrl())
                        .businessArea(businessAreaKeyword)
                        .serviceName(request.getServiceName())
                        .name(request.getName())
                        .take(request.getTake())
                        .mainAddress(request.getAddress1())
                        .mainZipCode(request.getZipCode1())
                        .subAddress(request.getAddress2())
                        .subZipCode(request.getZipCode2())
                        .managerName(request.getManager1())
                        .managerPhoneNo(request.getPhoneNumber1())
                        .subManagerName(request.getManager2())
                        .subManagerPhoneNo(request.getPhoneNumber2())
                        .workersCount(request.getWorkerNumber())
                        .email(request.getEmail())
                        .fax(request.getFax())
                        .bizNo(request.getBusinessNumber())
                        .representative(request.getRepresentativeName())
                        .foundedAt(request.getFoundedAt())
                        .build()
        );


        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getAuthority());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(), user.getAuthority());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessExpiresAt(jwtTokenProvider.getExpiredAt(TokenType.ACCESS))
                .refreshToken(refreshToken)
                .refreshExpiresAt(jwtTokenProvider.getExpiredAt(TokenType.REFRESH))
                .authority(Authority.COMPANY)
                .build();
    }
}
