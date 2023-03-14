package com.example.jobis.domain.application.presentation;

import com.example.jobis.domain.application.presentation.dto.request.CreateApplicationRequest;
import com.example.jobis.domain.application.presentation.dto.response.TeacherQueryApplicationsResponse;
import com.example.jobis.domain.application.presentation.dto.response.QueryCompanyApplicationsResponse;
import com.example.jobis.domain.application.presentation.dto.response.StudentApplicationsResponse;
import com.example.jobis.domain.application.domain.enums.ApplicationStatus;
import com.example.jobis.domain.application.service.CreateApplicationService;
import com.example.jobis.domain.application.service.DeleteApplicationService;
import com.example.jobis.domain.application.service.QueryCompanyApplicationsService;
import com.example.jobis.domain.application.service.QueryStudentApplicationsService;
import com.example.jobis.domain.application.service.TeacherQueryApplicationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/applications")
@RestController
public class ApplicationController {

    private final CreateApplicationService createApplicationService;
    private final DeleteApplicationService deleteApplicationService;
    private final TeacherQueryApplicationsService queryApplicationListService;
    private final QueryCompanyApplicationsService queryCompanyApplicationsService;
    private final QueryStudentApplicationsService queryStudentApplicationsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{recruitment-id}")
    public void createApplication(
            @RequestBody @Valid CreateApplicationRequest request,
            @PathVariable("recruitment-id") UUID recruitmentId
    ) {
        createApplicationService.execute(request, recruitmentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{application-id}")
    public void deleteApplication(@PathVariable("application-id") UUID applicationId) {
        deleteApplicationService.execute(applicationId);
    }

    @GetMapping
    public List<TeacherQueryApplicationsResponse> queryTeacherApplicationList (
            @RequestParam(value = "application-status", required = false) ApplicationStatus applicationStatus,
            @RequestParam(value = "student-name", required = false) String studentName
    ) {
        return queryApplicationListService.execute(applicationStatus, studentName);
    }

    @GetMapping("/company")
    public List<QueryCompanyApplicationsResponse> queryCompanyApplicationList() {
        return queryCompanyApplicationsService.execute();
    }

    @GetMapping("/students")
    public List<StudentApplicationsResponse> queryApplication() {
        return queryStudentApplicationsService.execute();
    }
}
