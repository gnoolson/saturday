package gnoolson.saturday.app.web.broker;

import gnoolson.saturday.app.web.broker.dto.ConnectedClientDto;
import gnoolson.saturday.app.web.broker.dto.MQTTBrokerDto;
import gnoolson.saturday.app.web.broker.dto.StartMQTTBrokerDto;
import gnoolson.saturday.app.web.broker.dto.UserDto;
import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.user.dto.DeleteUserDto;
import gnoolson.saturday.broker.port.inbount.*;
import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/mqtt-broker")
public class MQTTBrokerApiController {

    private final StartMQTTBrokerUseCase startMQTTBrokerUseCase;
    private final ShutdownMQTTBrokerUseCase shutdownMQTTBrokerUseCase;
    private final GetMQTTBrokerUseCase getMQTTBrokerUseCase;
    private final GetConnectedMQTTClientsUseCase getConnectedMQTTClientsUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    /*
     *
     *
     * */
    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/connected-client/all")
    public List<ConnectedClientDto> getConnectedClients() {
        List<GetConnectedMQTTClientsUseCase.ClientDto> clientList = getConnectedMQTTClientsUseCase.execute();
        return clientList.stream().map(dto -> new ConnectedClientDto(
                dto.getName().getValue(),
                dto.getUsername().getValue(),
                dto.getConnected().getValue()
        )).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/user/delete")
    public ResponseEntity<Object> deleteUser(@RequestBody @Valid DeleteUserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            deleteUserUseCase.execute(AuthUsername.of(userDto.getUsername()));
        } catch (Exception e) {
            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            createUserUseCase.execute(new CreateUserUseCase.UserDto(AuthUsername.of(userDto.getUsername()), AuthPassword.of(userDto.getPassword())));
        } catch (Exception e) {
            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/all")
    public List<UserDto> getUsers() {
        List<GetUsersUseCase.UserDto> users = getUsersUseCase.execute();
        return users.stream().map(dto -> new UserDto(
                dto.getUsername().getValue(),
                null
        )).collect(Collectors.toList());
    }

    @GetMapping
    public MQTTBrokerDto getMQTTBroker() {
        GetMQTTBrokerUseCase.BrokerDto brokerDto = getMQTTBrokerUseCase.execute();
        return new MQTTBrokerDto(brokerDto.isStarted(), brokerDto.isAllowAnonymousConnections(), brokerDto.getPort().getValue());
    }

    @PostMapping("/start")
    public ResponseEntity<Object> start(@RequestBody @Valid StartMQTTBrokerDto startMQTTBrokerDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            startMQTTBrokerUseCase.execute(new StartMQTTBrokerUseCase.StartMQTTBrokerDto(
                    startMQTTBrokerDto.isAllowAnonymousConnections(),
                    PositiveNumber.of(startMQTTBrokerDto.getPort())
            ));
        } catch (Exception e) {
            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shutdown")
    public ResponseEntity<Object> shutdown() {
        try {
            shutdownMQTTBrokerUseCase.execute();
        } catch (Exception e) {
            List<ValidationResultDto> collect = Collections.singletonList(new ValidationResultDto("service", e.getMessage()));
            return ResponseEntity.badRequest().body(collect);
        }
        return ResponseEntity.ok().build();
    }

}
