package team.retum.jobis.domain.acceptance.persistence.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.retum.jobis.domain.company.persistence.entity.CompanyEntity;
import team.retum.jobis.domain.student.persistence.entity.StudentEntity;
import team.retum.jobis.global.converter.StringListConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_acceptance")
@Entity
public class AcceptanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "YEAR")
    private Integer year;

    @NotNull
    @Column(columnDefinition = "VARCHAR(10)")
    private String studentName;

    @NotNull
    @Column(columnDefinition = "VARCHAR(20)")
    private String businessArea;

    @NotNull
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "VARCHAR(500)")
    private List<String> tech;

    @NotNull
    @Column(columnDefinition = "CHAR(4)")
    private String studentGcn;

    @NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate contractDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @Builder
    public AcceptanceEntity(Long id, Integer year, String studentName, String businessArea, List<String> tech,
                            String studentGcn, LocalDate contractDate, CompanyEntity companyEntity, StudentEntity student) {
        this.id = id;
        this.year = year;
        this.studentName = studentName;
        this.businessArea = businessArea;
        this.tech = tech;
        this.studentGcn = studentGcn;
        this.contractDate = contractDate;
        this.company = companyEntity;
        this.student = student;
    }
}
