package gnoolson.saturday.app.web.user;

import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.user.dto.*;
import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.vo.Password;
import gnoolson.saturday.user.port.inbound.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final GetUsersUseCase getUsersUseCase;
    private final CreateFirstEditorUseCase createFirstEditorUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final SetUserRoleUseCase setUserRoleUseCase;
    private final SetUserPasswordUseCase setUserPasswordUseCase;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DeleteUserUseCase deleteUserUseCase;

    /*
     *
     *
     * */
    @GetMapping("/all")
    public List<UserDto> getUsers(Principal principal) {
        List<GetUsersUseCase.UserDto> result = getUsersUseCase.execute();

        return result.stream().map((userDto) -> {
            boolean you = principal.getName().equals(userDto.getUsername().getValue());
            return new UserDto(userDto.getUsername().getValue(), userDto.getRole().name(), you);
        }).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/create")
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid CreateUserDto createUserDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            createUserUseCase.execute(new CreateUserUseCase.CreateUserDto(
                    Username.of(createUserDto.getUsername()),
                    Password.of(passwordEncoder.encode(createUserDto.getPassword())),
                    CreateUserUseCase.Role.valueOf(createUserDto.getRole())
            ));
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/set-role")
    public ResponseEntity<Object> setUserRole(@RequestBody @Valid SetUserRoleDto setUserRoleDto, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            setUserRoleUseCase.execute(new SetUserRoleUseCase.SetUserRoleDto(
                    Username.of(setUserRoleDto.getUsername()),
                    SetUserRoleUseCase.Role.valueOf(setUserRoleDto.getRole())
            ), Username.of(principal.getName()));
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/set-password")
    public ResponseEntity<Object> setUserPassword(@RequestBody @Valid SetUserPasswordDto setUserPasswordDto, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            setUserPasswordUseCase.execute(new SetUserPasswordUseCase.SetUserPasswordDto(
                    Username.of(setUserPasswordDto.getUsername()),
                    Password.of(passwordEncoder.encode(setUserPasswordDto.getPassword()))
            ), Username.of(principal.getName()));
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestBody @Valid DeleteUserDto deleteUserDto, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            deleteUserUseCase.execute(
                    Username.of(deleteUserDto.getUsername()),
                    Username.of(principal.getName()));
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception", e);

            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-editor")
    public ResponseEntity<Object> createRootUser(@RequestBody @Valid CreateEditorDto createEditorDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            createFirstEditorUseCase.execute(new CreateFirstEditorUseCase.CreateUserDto(
                    Username.of(createEditorDto.getUsername()),
                    Password.of(passwordEncoder.encode(createEditorDto.getPassword()))
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
