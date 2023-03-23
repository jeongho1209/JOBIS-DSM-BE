package team.returm.jobis.domain.company.presentation.dto.response;

import team.returm.jobis.domain.company.domain.repository.vo.StudentQueryCompaniesVO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentQueryCompaniesResponse {
    private final List<StudentQueryCompaniesVO> companies;
}