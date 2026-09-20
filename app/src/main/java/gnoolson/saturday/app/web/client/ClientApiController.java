package gnoolson.saturday.app.web.client;

import gnoolson.saturday.app.web.client.dto.ClientDto;
import gnoolson.saturday.app.web.client.dto.ClientMapper;
import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.client.port.inbound.*;
import gnoolson.saturday.common.model.vo.ClientId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/client")
public class ClientApiController {

    private final GetAllClientsUseCase getAllClientsUseCase;
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final ControlClientUseCase controlClientUseCase;

    /*
     *
     *
     * */
    @GetMapping("/all")
    public Collection<ClientDto> getClientsData() {
        List<GetAllClientsUseCase.ClientDto> clients = getAllClientsUseCase.execute();
        return ClientMapper.toDto(clients);
    }

    @GetMapping("/{id}")
    public ClientDto getClientData(@PathVariable UUID id) {
        ClientDto clientDto;
        if (id.equals(ClientId.empty().getValue())) {
            clientDto = new ClientDto();
        } else {
            GetClientUseCase.ClientDto client = getClientUseCase.execute(ClientId.of(id));
            clientDto = new ClientDto(client);
        }
        return clientDto;
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/{id}/enable")
    public void enableAction(@PathVariable UUID id, @RequestParam("flag") boolean flag) {
        controlClientUseCase.execute(ClientId.of(id), flag);
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/upsert")
    public ResponseEntity<Object> upsertAction(@RequestBody @Valid ClientDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            if (clientDto.getId().equals(ClientId.empty().getValue())) {
                CreateClientUseCase.ClientDto client = ClientMapper.toDomainDtoForCreateUseCase(clientDto);
                createClientUseCase.execute(client);
            } else {
                UpdateClientUseCase.ClientDto client = ClientMapper.toDomainForUpdateUseCase(clientDto);
                updateClientUseCase.execute(client);
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
    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteAction(@PathVariable UUID id) {
        if (deleteClientUseCase.execute(ClientId.of(id)))
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().body("Something wrong");
    }

}
