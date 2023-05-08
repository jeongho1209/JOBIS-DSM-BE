package team.returm.jobis.domain.recruitment.domain.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PayInfo {

    @NotNull
    @Column(columnDefinition = "INT")
    private Integer trainingPay;

    @Column(columnDefinition = "INT")
    private Integer pay;
}
