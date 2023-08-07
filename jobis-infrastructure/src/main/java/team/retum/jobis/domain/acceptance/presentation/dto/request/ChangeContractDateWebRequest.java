package team.retum.jobis.domain.acceptance.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.retum.jobis.domain.acceptance.dto.request.ChangeContractDateRequest;
import team.retum.jobis.global.annotation.ValidListElements;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChangeContractDateWebRequest {

    @ValidListElements
    private List<Long> acceptanceIds;

    @NotNull
    private LocalDate contractDate;

    public ChangeContractDateRequest toDomainRequest() {
        return ChangeContractDateRequest.builder()
                .acceptanceIds(this.acceptanceIds)
                .contractDate(this.contractDate)
                .build();
    }
}