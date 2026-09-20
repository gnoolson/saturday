package gnoolson.saturday.app.web.script;



import com.fasterxml.jackson.databind.JsonNode;
import gnoolson.saturday.app.script.Dev;
import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.script.dto.*;
import gnoolson.saturday.app.web.script.dto.validation.RowsValidator;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.lua_script_executor.utils.LuaErrorUtil;
import gnoolson.saturday.script.model.exception.TestDataException;
import gnoolson.saturday.script.model.vo.Code;
import gnoolson.saturday.script.port.inbound.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/script")
public class ScriptApiController {

    private final GetAllScriptsUseCase getAllScriptsUseCase;
    private final GetScriptUseCase getScriptUseCase;
    private final CreateScriptUseCase createScriptUseCase;
    private final UpdateScriptUseCase updateScriptUseCase;
    private final GetNumberOfErrorsUseCase getNumberOfErrorsUseCase;
    private final GetScriptErrorsCounterUseCase getScriptErrorsCounterUseCase;
    private final Dev dev;
    private final DeleteErrorsAndUnblockUseCase deleteErrorsAndUnblockUseCase;
    private final DeleteScriptUseCase deleteScriptUseCase;
    private final ScriptControlUseCase scriptControlUseCase;
    private final GetLogTextUseCase getLogTextUseCase;
    private final ClearLogFileUseCase clearLogFileUseCase;
    private final GetDefaultScriptCodeUseCase getDefaultScriptCodeUseCase;
    private final BeautifyCodeUseCase beautifyCodeUseCase;
    private final UpdateCodeUseCase updateCodeUseCase;
    private final IsScriptEnabledUseCase isScriptEnabledUseCase;
    private final SetDebugEnabledUseCase setDebugEnabledUseCase;
    private final IsDebugEnabledUseCase isDebugEnabledUseCase;

    /*
     *
     *
     * */
    @GetMapping(value = "/{id}/log")
    public String getLogText(@PathVariable UUID id, @RequestParam(name = "rows", defaultValue = "1000") int rows) {
        RowsValidator.check(rows);
        Optional<String> textOpt = getLogTextUseCase.execute(ScriptId.of(id), PositiveNumber.of(rows));
        return textOpt.orElse(StringUtils.EMPTY);
    }

    @PostMapping(value = "/{id}/log/clear")
    public ResponseEntity<String> clearLog(@PathVariable UUID id) {
        if (clearLogFileUseCase.execute(ScriptId.of(id)))
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().body("Something wrong");
    }

    @PostMapping(value = "/{id}/log/debug-level")
    public void setDebugEnabled(@PathVariable UUID id, @RequestParam("flag") boolean flag) {
        setDebugEnabledUseCase.execute(ScriptId.of(id), flag);
    }

    @GetMapping(value = "/{id}/log/debug-level")
    public boolean isDebugEnabled(@PathVariable UUID id) {
        return isDebugEnabledUseCase.execute(ScriptId.of(id));
    }

    @GetMapping("/all")
    public Collection<ScriptDto> getScripts() {
        List<GetAllScriptsUseCase.ScriptDto> scripts = getAllScriptsUseCase.execute();
        return ScriptMapper.toDto(scripts);
    }

    @GetMapping({"/{id}"})
    public ScriptDto getScript(@PathVariable UUID id) {
        ScriptDto scriptDto;
        if (id.equals(ScriptId.empty().getValue())) {
            scriptDto = new ScriptDto(getDefaultScriptCodeUseCase.execute());
        } else {
            GetScriptUseCase.ScriptDto script = getScriptUseCase.execute(ScriptId.of(id));
            scriptDto = new ScriptDto(script);
        }
        return scriptDto;
    }

    @GetMapping({"/{id}/name"})
    public String getScriptName(@PathVariable UUID id) {
        GetScriptUseCase.ScriptDto script = getScriptUseCase.execute(ScriptId.of(id));
        return script.getName().getValue();
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/upsert")
    public ResponseEntity<Object> upsert(@RequestBody @Valid ScriptDto scriptDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            if (scriptDto.getId().equals(ScriptId.empty().getValue())) {
                CreateScriptUseCase.ScriptDto script = ScriptMapper.toDomainDtoForCreateUseCase(scriptDto);
                createScriptUseCase.execute(script);
            } else {
                UpdateScriptUseCase.ScriptDto script = ScriptMapper.toDomainDtoForUpdateUseCase(scriptDto);
                updateScriptUseCase.execute(script);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping({"/{id}/errors/counter"})
    public int getScriptErrorsCounter(@PathVariable UUID id) {
        PositiveNumber counter = getScriptErrorsCounterUseCase.execute(ScriptId.of(id));
        return counter.getValue();
    }

    @GetMapping({"/{id}/errors"})
    public List<ScriptErrorDto> getScriptErrors(@PathVariable UUID id) {
        List<GetNumberOfErrorsUseCase.ScriptErrorDto> errors = getNumberOfErrorsUseCase.execute(ScriptId.of(id));
        return ScriptErrorMapper.toDto(errors);
    }

    @PostMapping({"/{id}/errors/delete"})
    public ResponseEntity<String> deleteErrors(@PathVariable UUID id) {
        if (deleteErrorsAndUnblockUseCase.execute(ScriptId.of(id)))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Something wrong");
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping({"/dev"})
    public ResponseEntity<Object> dev(@RequestBody JsonNode jsonObject) {
        try {
            Dev.RequestDto requestDto = DevRequestDtoMapper.toDomainDto(jsonObject);
            Dev.ResponseDto responseDto = dev.execute(requestDto);
            JsonNode response = DevRequestDtoMapper.toDto(responseDto);

            return ResponseEntity.ok(response);
        } catch (TestDataException e) {
            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("mock_data_code_area", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("code", LuaErrorUtil.getMessage(e)));
            return ResponseEntity.badRequest().body(collect);
        }
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping(value = "/beautify")
    public ResponseEntity<Object> beautify(@RequestBody @NotNull @Length(max = 50000, message = "{script.upsert.form.code.error}") String code) {
        try {
            Code result = beautifyCodeUseCase.execute(Code.of(code));
            return ResponseEntity.ok(result.getValue());
        } catch (Exception e) {
            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("code", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/update-code")
    public ResponseEntity<Object> updateCodeAction(@RequestBody @Valid CodeDto codeDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            List<ScriptId> includedScripts = codeDto.getIncludedScripts().stream().map(ScriptId::of).collect(Collectors.toList());
            updateCodeUseCase.execute(ScriptId.of(codeDto.getId()), Code.of(codeDto.getCode()), includedScripts);
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping({"/{id}/delete"})
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (deleteScriptUseCase.execute(ScriptId.of(id)))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Something wrong");
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/{id}/enable")
    public ResponseEntity<Object> enable(@PathVariable UUID id, @RequestParam("flag") boolean flag) {
        try {
            scriptControlUseCase.execute(ScriptId.of(id), flag);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("code", e.getMessage())); // for DEV
            return ResponseEntity.badRequest().body(collect);
        }
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/{id}/is_enabled")
    public boolean isEnabled(@PathVariable UUID id) {
        boolean result = isScriptEnabledUseCase.execute(ScriptId.of(id));
        return result;
    }

}
