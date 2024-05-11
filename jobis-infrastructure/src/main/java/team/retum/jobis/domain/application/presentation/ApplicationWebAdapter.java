package team.retum.jobis.domain.application.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.retum.jobis.common.dto.response.TotalPageCountResponse;
import team.retum.jobis.domain.application.dto.request.AttachmentRequest;
import team.retum.jobis.domain.application.dto.response.ApplicationCountResponse;
import team.retum.jobis.domain.application.dto.response.CompanyQueryApplicationsResponse;
import team.retum.jobis.domain.application.dto.response.QueryEmploymentCountResponse;
import team.retum.jobis.domain.application.dto.response.QueryMyApplicationsResponse;
import team.retum.jobis.domain.application.dto.response.QueryPassedApplicationStudentsResponse;
import team.retum.jobis.domain.application.dto.response.QueryRejectionReasonResponse;
import team.retum.jobis.domain.application.dto.response.TeacherQueryApplicationsResponse;
import team.retum.jobis.domain.application.model.ApplicationStatus;
import team.retum.jobis.domain.application.presentation.dto.request.ChangeApplicationsStatusWebRequest;
import team.retum.jobis.domain.application.presentation.dto.request.ChangeFieldTrainDateWebRequest;
import team.retum.jobis.domain.application.presentation.dto.request.CreateApplicationWebRequest;
import team.retum.jobis.domain.application.presentation.dto.request.RejectApplicationWebRequest;
import team.retum.jobis.domain.application.usecase.ChangeApplicationsStatusUseCase;
import team.retum.jobis.domain.application.usecase.ChangeFieldTrainDateUseCase;
import team.retum.jobis.domain.application.usecase.CompanyQueryApplicationsUseCase;
import team.retum.jobis.domain.application.usecase.CreateApplicationUseCase;
import team.retum.jobis.domain.application.usecase.DeleteApplicationUseCase;
import team.retum.jobis.domain.application.usecase.QueryEmploymentCountUseCase;
import team.retum.jobis.domain.application.usecase.QueryMyApplicationsUseCase;
import team.retum.jobis.domain.application.usecase.QueryPassedApplicationStudentsUseCase;
import team.retum.jobis.domain.application.usecase.QueryRejectionReasonUseCase;
import team.retum.jobis.domain.application.usecase.ReapplyUseCase;
import team.retum.jobis.domain.application.usecase.RejectApplicationUseCase;
import team.retum.jobis.domain.application.usecase.TeacherQueryApplicationsUseCase;

import java.time.Year;

import static team.retum.jobis.global.config.cache.CacheName.APPLICATION;
import static team.retum.jobis.global.config.cache.CacheName.COMPANY;
import static team.retum.jobis.global.config.cache.CacheName.RECRUITMENT;

@CacheConfig(cacheNames = APPLICATION)
@RequiredArgsConstructor
@RequestMapping("/applications")
@RestController
public class ApplicationWebAdapter {

    private final CreateApplicationUseCase createApplicationUseCase;
    private final DeleteApplicationUseCase deleteApplicationUseCase;
    private final TeacherQueryApplicationsUseCase queryApplicationListService;
    private final CompanyQueryApplicationsUseCase companyQueryApplicationsUseCase;
    private final QueryMyApplicationsUseCase queryMyApplicationsUseCase;
    private final ChangeApplicationsStatusUseCase changeApplicationsStatusUseCase;
    private final ChangeFieldTrainDateUseCase changeFieldTrainDateUseCase;
    private final RejectApplicationUseCase rejectApplicationUseCase;
    private final QueryEmploymentCountUseCase queryEmploymentCountUseCase;
    private final QueryPassedApplicationStudentsUseCase queryPassedApplicationStudentsUseCase;
    private final ReapplyUseCase reapplyUseCase;
    private final QueryRejectionReasonUseCase queryRejectionReasonUseCase;

    @Caching(
        evict = {
            @CacheEvict(cacheNames = APPLICATION, allEntries = true),
            @CacheEvict(cacheNames = COMPANY, allEntries = true),
            @CacheEvict(cacheNames = RECRUITMENT, allEntries = true)
        }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{recruitment-id}")
    public void createApplication(
        @RequestBody @Valid CreateApplicationWebRequest request,
        @PathVariable("recruitment-id") Long recruitmentId
    ) {
        createApplicationUseCase.execute(
            recruitmentId,
            request.getAttachments().stream()
                .map(attachment -> new AttachmentRequest(attachment.getUrl(), attachment.getType()))
                .toList()
        );
    }

    @Caching(
        evict = {
            @CacheEvict(cacheNames = APPLICATION, allEntries = true),
            @CacheEvict(cacheNames = COMPANY, allEntries = true)
        }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{application-id}")
    public void deleteApplication(@PathVariable("application-id") Long applicationId) {
        deleteApplicationUseCase.execute(applicationId);
    }

    @Cacheable
    @GetMapping
    public TeacherQueryApplicationsResponse queryTeacherApplicationList(
        @RequestParam(value = "application_status", required = false) ApplicationStatus applicationStatus,
        @RequestParam(value = "student_name", required = false) String studentName,
        @RequestParam(value = "recruitment_id", required = false) Long recruitmentId,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy") Year year
    ) {
        return queryApplicationListService.execute(applicationStatus, studentName, recruitmentId, year);
    }

    @GetMapping("/teacher/count")
    public ApplicationCountResponse countApplications() {
        return queryApplicationListService.countApplications();
    }

    @Cacheable
    @GetMapping("/count")
    public TotalPageCountResponse queryApplicationCount(
        @RequestParam(value = "application_status", required = false) ApplicationStatus applicationStatus,
        @RequestParam(value = "student_name", required = false) String studentName
    ) {
        return queryApplicationListService.getTotalPageCount(applicationStatus, studentName);
    }

    @GetMapping("/company")
    public CompanyQueryApplicationsResponse queryCompanyApplicationList() {
        return companyQueryApplicationsUseCase.execute();
    }

    @GetMapping("/students")
    public QueryMyApplicationsResponse queryMyApplications() {
        return queryMyApplicationsUseCase.execute();
    }

    @Caching(
        evict = {
            @CacheEvict(cacheNames = APPLICATION, allEntries = true),
            @CacheEvict(cacheNames = COMPANY, allEntries = true)
        }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/status")
    public void changeApplicationsStatus(@RequestBody @Valid ChangeApplicationsStatusWebRequest request) {
        changeApplicationsStatusUseCase.execute(
            request.getApplicationIds(),
            request.getStatus()
        );
    }

    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/train-date")
    public void changeFieldTrainDate(@RequestBody @Valid ChangeFieldTrainDateWebRequest request) {
        changeFieldTrainDateUseCase.execute(request.toDomainRequest());
    }

    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/rejection/{application-id}")
    public void rejectApplication(
        @PathVariable("application-id") Long applicationId,
        @Valid @RequestBody RejectApplicationWebRequest request
    ) {
        rejectApplicationUseCase.execute(applicationId, request.getReason());
    }

    @Cacheable
    @GetMapping("/employment/count")
    public QueryEmploymentCountResponse queryEmploymentCount() {
        return queryEmploymentCountUseCase.execute();
    }

    @Cacheable
    @GetMapping("/pass/{company-id}")
    public QueryPassedApplicationStudentsResponse queryFieldTrainApplication(
        @PathVariable("company-id") Long companyId
    ) {
        return queryPassedApplicationStudentsUseCase.execute(companyId);
    }

    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{application-id}")
    public void reapply(
        @PathVariable("application-id") Long applicationId,
        @RequestBody CreateApplicationWebRequest request
    ) {
        reapplyUseCase.execute(
            applicationId,
            request.getAttachments().stream()
                .map(attachment -> new AttachmentRequest(attachment.getUrl(), attachment.getType()))
                .toList()
        );
    }

    @Cacheable
    @GetMapping("/rejection/{application-id}")
    public QueryRejectionReasonResponse queryRejectionReason(@PathVariable("application-id") Long applicationId) {
        return queryRejectionReasonUseCase.execute(applicationId);
    }
}
