package team.returm.jobis.domain.company.presentation.dto.request;

import team.returm.jobis.global.util.RegexProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterCompanyRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 10, max = 10)
    private String businessNumber;

    @NotNull
    @Pattern(regexp = RegexProperty.COMPANY_PASSWORD)
    private String password;

    @NotEmpty
    @Size(max = 300)
    private String companyIntroduce;

    @NotBlank
    @Size(min = 5, max = 5)
    private String zipCode1;

    @NotBlank
    @Size(max = 100)
    private String address1;

    @Size(max = 5)
    private String zipCode2;

    @Size(max = 100)
    private String address2;

    @NotBlank
    @Size(max = 10)
    private String manager1;

    @NotBlank
    @Size(min = 10, max = 12)
    private String phoneNumber1;

    @Size(max = 10)
    private String manager2;

    @Size(max = 12)
    private String phoneNumber2;

    @Size(min = 10, max = 12)
    private String fax;

    @Email
    @Size(min = 1, max = 30)
    private String email;

    @NotBlank
    @Size(max = 40)
    private String representativeName;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundedAt;

    @NotNull
    private int workerNumber;

    @NotNull
    private int take;

    @NotBlank
    private String companyProfileUrl;

}