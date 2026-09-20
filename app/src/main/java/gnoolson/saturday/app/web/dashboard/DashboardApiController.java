package gnoolson.saturday.app.web.dashboard;

import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.app.web.dashboard.dto.DashboardDto;
import gnoolson.saturday.app.web.dashboard.dto.DashboardMapper;
import gnoolson.saturday.app.web.dashboard.dto.HtmlDto;
import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.dashboard.model.OpenDashboardRequest;
import gnoolson.saturday.dashboard.model.exception.NotAuthorized;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {

    private final PostDashboardDataUseCase postDashboardDataUseCase;
    private final GetAllDashboardsUseCase getAllDashboardsUseCase;
    private final CreateDashboardUseCase createDashboardUseCase;
    private final GetDefaultHtmlTemplateUseCase getDefaultHtmlTemplateUseCase;
    private final UpdateDashboardUseCase updateDashboardUseCase;
    private final GetDashboardUseCase getDashboardUseCase;
    private final DeleteDashboardUseCase deleteDashboardUseCase;
    private final UpdateHtmlUseCase updateHtmlUseCase;
    private final RegistrationOpenDashboardUseCase registrationOpenDashboardUseCase;
    private final RequestTimeoutUseCase requestTimeoutUseCase;

    /*
     *
     *
     * */
    @GetMapping({"/{id}"})
    public DashboardDto getDashboardData(@PathVariable UUID id) {
        DashboardDto dashboardDto;
        if (id.equals(DashboardId.empty().getValue())) {
            dashboardDto = new DashboardDto(getDefaultHtmlTemplateUseCase.execute().getValue());
        } else {
            GetDashboardUseCase.DashboardDto dashboard = getDashboardUseCase.execute(DashboardId.of(id));
            dashboardDto = DashboardMapper.toDto(dashboard);
        }
        return dashboardDto;
    }

    @GetMapping(value = "/all")
    public List<DashboardDto> getAllDashboardData() {
        List<GetAllDashboardsUseCase.DashboardDto> dashboardDtoList = getAllDashboardsUseCase.execute();
        return DashboardMapper.toDto2(dashboardDtoList);
    }

    @GetMapping(value = "/{dashboardId}/{openDashboardId}/message", produces = "application/json")
    public DeferredResult<ResponseEntity<?>> getMessage(@PathVariable UUID dashboardId,
                                                        @PathVariable UUID openDashboardId) throws NotAuthorized {

        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(30_000L);

        OpenDashboardRequest openDashboardRequest = new OpenDashboardRequest() {
            @Override
            public boolean setResult(Object data) {
                String json = JSON.toJSONString(data);
                return deferredResult.setResult(ResponseEntity.ok(json));
            }
        };

        deferredResult.onTimeout(() -> {
            requestTimeoutUseCase.execute(openDashboardRequest.getId());
            deferredResult.setErrorResult(ResponseEntity.noContent().build());
        });

        registrationOpenDashboardUseCase.execute(OpenDashboardId.of(openDashboardId, DashboardId.of(dashboardId)), openDashboardRequest);

        return deferredResult;
    }

    @PostMapping(value = "/{dashboardId}/{openDashboardId}/execute", consumes = "application/json")
    public void execute(@PathVariable UUID dashboardId,
                        @PathVariable UUID openDashboardId,
                        @RequestBody Map<String, Object> actionData) throws NotAuthorized {

        postDashboardDataUseCase.execute(DashboardId.of(dashboardId), OpenDashboardId.of(openDashboardId, DashboardId.of(dashboardId)), actionData, SecurityUtil.getRole());
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/upsert")
    public ResponseEntity<Object> upsertAction(@RequestBody @Valid DashboardDto dashboardDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            if (dashboardDto.getId().equals(DashboardId.empty().getValue())) {
                CreateDashboardUseCase.DashboardDto dashboard = DashboardMapper.toDomainDtoForCreateUseCase(dashboardDto);
                createDashboardUseCase.execute(dashboard);
            } else {
                UpdateDashboardUseCase.DashboardDto dashboard = DashboardMapper.toDomainDtoForUpdateUseCase(dashboardDto);
                updateDashboardUseCase.execute(dashboard);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/update-html")
    public ResponseEntity<Object> updateHtmlAction(@RequestBody @Valid HtmlDto htmlDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            updateHtmlUseCase.execute(DashboardId.of(htmlDto.getId()), Html.of(htmlDto.getHtml()));
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteAction(@PathVariable UUID id) {
        if (deleteDashboardUseCase.execute(DashboardId.of(id)))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Something wrong");
    }


}
