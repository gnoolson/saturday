package gnoolson.saturday.app.web.create_super;

import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.create_super.dto.CreateSuperDto;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.vo.Password;
import gnoolson.saturday.user.port.inbound.CreateFirstSuperUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/create-super")
public class CreateSuperApiController {

    private final CreateFirstSuperUseCase createFirstSuperUseCase;
    private final BCryptPasswordEncoder passwordEncoder;

    /*
    *
    *
    * */
    @PostMapping
    public ResponseEntity<Object> createSuperUser(@RequestBody @Valid CreateSuperDto createSuperDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            createFirstSuperUseCase.execute(new CreateFirstSuperUseCase.CreateUserDto(
                    Username.of(createSuperDto.getUsername()),
                    Password.of(passwordEncoder.encode(createSuperDto.getPassword()))
            ));
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

}
