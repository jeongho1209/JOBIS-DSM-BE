package team.retum.jobis.domain.company.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.retum.jobis.domain.company.dto.request.UpdateCompanyDetailsRequest;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateCompanyDetailsWebRequest {

    @Size(max = 4000)
    private String companyIntroduce;

    @NotBlank
    @Size(min = 5, max = 5)
    private String mainZipCode;

    @NotBlank
    @Size(max = 50)
    private String mainAddress;

    @NotBlank
    @Size(max = 50)
    private String mainAddressDetail;

    @Size(max = 5)
    private String subZipCode;

    @Size(max = 50)
    private String subAddress;

    @Size(max = 50)
    private String subAddressDetail;

    @NotBlank
    @Size(max = 10)
    private String managerName;

    @NotBlank
    @Size(min = 10, max = 12)
    private String managerPhoneNo;

    @Size(max = 10)
    private String subManagerName;

    @Size(max = 12)
    private String subManagerPhoneNo;

    @Size(max = 12)
    private String fax;

    @Email
    @Size(max = 30)
    private String email;

    @NotNull
    private int workerNumber;

    @NotNull
    private double take;

    @NotBlank
    private String companyProfileUrl;

    @NotBlank
    private String serviceName;

    @Size(min = 10, max = 12)
    @NotBlank
    private String representativePhoneNo;

    @NotNull
    private Long businessAreaCode;

    @NotBlank
    private String bizRegistrationUrl;

    private List<@NotNull String> attachmentUrls;


    public UpdateCompanyDetailsRequest toDomainRequest() {
        return UpdateCompanyDetailsRequest.builder()
                .companyIntroduce(this.companyIntroduce)
                .mainZipCode(this.mainZipCode)
                .mainAddress(this.mainAddress)
                .mainAddressDetail(this.mainAddressDetail)
                .subZipCode(this.subZipCode)
                .subAddress(this.subAddress)
                .subAddressDetail(this.subAddressDetail)
                .managerName(this.managerName)
                .managerPhoneNo(this.managerPhoneNo)
                .subManagerName(this.subManagerName)
                .subManagerPhoneNo(this.subManagerPhoneNo)
                .fax(this.fax)
                .email(this.email)
                .workerNumber(this.workerNumber)
                .take(this.take)
                .companyProfileUrl(this.companyProfileUrl)
                .serviceName(this.serviceName)
                .representativePhoneNo(this.representativePhoneNo)
                .representativePhoneNo(this.representativePhoneNo)
                .businessAreaCode(this.businessAreaCode)
                .bizRegistrationUrl(this.bizRegistrationUrl)
                .attachmentUrls(this.attachmentUrls)
                .build();
    }
}
