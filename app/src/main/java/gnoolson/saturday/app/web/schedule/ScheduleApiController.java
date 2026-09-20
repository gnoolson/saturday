package gnoolson.saturday.app.web.schedule;

import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.schedule.dto.ScheduleDto;
import gnoolson.saturday.app.web.schedule.dto.ScheduleMapper;
import gnoolson.saturday.common.model.vo.ScheduleId;
import gnoolson.saturday.schedule.port.inbound.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/schedule")
public class ScheduleApiController {

    private final GetAllSchedulesUseCase getAllSchedulesUseCase;
    private final CreateScheduleUseCase createScheduleUseCase;
    private final UpdateScheduleUseCase updateScheduleUseCase;
    private final GetScheduleUseCase getScheduleUseCase;
    private final ScheduleControlUseCase scheduleControlUseCase;
    private final DeleteScheduleUseCase deleteScheduleUseCase;

    /*
     *
     *
     * */
    @GetMapping("/all")
    public List<ScheduleDto> getAllSchedulesData() {
        List<GetAllSchedulesUseCase.ScheduleDto> schedules = getAllSchedulesUseCase.execute();
        return schedules.stream().map(ScheduleMapper::toDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/{id}"})
    public ScheduleDto getScheduleData(@PathVariable UUID id) {
        ScheduleDto scheduleDto;
        if (id.equals(ScheduleId.empty().getValue())) {
            scheduleDto = new ScheduleDto();
        } else {
            GetScheduleUseCase.ScheduleDto domainDto = getScheduleUseCase.execute(ScheduleId.of(id));
            scheduleDto = ScheduleMapper.toDto(domainDto);
        }
        return scheduleDto;
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/upsert")
    public ResponseEntity<Object> upsertAction(@RequestBody @Valid ScheduleDto scheduleDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            if (scheduleDto.getId().equals(ScheduleId.empty().getValue())) {
                CreateScheduleUseCase.ScheduleDto domainDto = ScheduleMapper.toDomainDtoForCreate(scheduleDto);
                createScheduleUseCase.execute(domainDto);
            } else {
                UpdateScheduleUseCase.ScheduleDto domainDto = ScheduleMapper.toDomainDtoForUpdate(scheduleDto);
                updateScheduleUseCase.execute(domainDto);
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
    @PostMapping("/{id}/enable")
    public void enableAction(@PathVariable UUID id, @RequestParam("flag") boolean flag) {
        scheduleControlUseCase.execute(ScheduleId.of(id), flag);
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping({"/{id}/delete"})
    public ResponseEntity<String> deleteAction(@PathVariable UUID id) {
        if (deleteScheduleUseCase.execute(ScheduleId.of(id)))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Something wrong");
    }

}
