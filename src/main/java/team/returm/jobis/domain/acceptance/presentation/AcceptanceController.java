package team.returm.jobis.domain.acceptance.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.returm.jobis.domain.acceptance.presentation.dto.response.TeacherQueryFieldTraineesAndContractWorkersResponse;
import team.returm.jobis.domain.acceptance.service.TeacherQueryFieldTraineesAndContractWorkersService;

@RequiredArgsConstructor
@RequestMapping("/acceptance")
@RestController
public class AcceptanceController {

    private final TeacherQueryFieldTraineesAndContractWorkersService teacherQueryFieldTraineesAndContractWorkersService;

    @GetMapping("/field_trainees")
    public TeacherQueryFieldTraineesAndContractWorkersResponse teacherQueryFieldTraineesAndContractWorkers(
            @RequestParam(value = "company_id") Long companyId
    ) {
        return teacherQueryFieldTraineesAndContractWorkersService.execute(companyId);
    }

}
