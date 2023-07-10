package team.retum.jobis.domain.bug.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.retum.jobis.domain.bug.presentation.dto.request.CreateBugReportRequest;
import team.retum.jobis.domain.bug.presentation.dto.response.QueryBugReportDetailsResponse;
import team.retum.jobis.domain.bug.presentation.dto.response.QueryBugReportsResponse;
import team.retum.jobis.domain.bug.service.CreateBugReportService;
import team.retum.jobis.domain.bug.service.QueryBugReportDetailsService;
import team.retum.jobis.domain.bug.service.QueryBugReportsService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/bugs")
@RestController
public class BugController {

    private final CreateBugReportService createBugReportService;
    private final QueryBugReportsService queryBugReportsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBugReport(@RequestBody @Valid CreateBugReportRequest request) {
        createBugReportService.execute(request);
    }

    @GetMapping
    public QueryBugReportsResponse queryBugReports(
            @RequestParam(value = "development_area", required = false) DevelopmentArea developmentArea
    ) {
        return queryBugReportsService.execute(developmentArea);
    }
}
