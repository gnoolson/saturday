package gnoolson.saturday.app.web.subscription;

import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.subscription.dto.Mapper;
import gnoolson.saturday.app.web.subscription.dto.SubscriptionDto;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import gnoolson.saturday.subscription.port.inbound.*;
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

@Log4j2
@RequiredArgsConstructor
@RestController
public class SubscriptionApiController {

    private final GetAllSubscriptionsUseCase getAllSubscriptionsUseCase;
    private final GetSubscriptionUseCase getSubscriptionUseCase;
    private final UpdateSubscriptionUseCase updateSubscriptionUseCase;
    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    private final DeleteSubscriptionUseCase deleteSubscriptionUseCase;

    /*
     *
     *
     * */
    @GetMapping("/api/client/{clientId}/subscription/all")
    public List<SubscriptionDto> getAllSubscriptionsData(@PathVariable UUID clientId) {
        List<GetAllSubscriptionsUseCase.SubscriptionDto> execute = getAllSubscriptionsUseCase.execute(ClientId.of(clientId));
        List<SubscriptionDto> result = Mapper.toDto(execute);
        return result;
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/api/client/{clientId}/subscription/{subscriptionId}"})
    public SubscriptionDto getSubscriptionData(@PathVariable UUID clientId, @PathVariable UUID subscriptionId) {
        SubscriptionDto subscriptionDto;
        if (subscriptionId.equals(SubscriptionId.empty().getValue())) {
            subscriptionDto = new SubscriptionDto(ClientId.of(clientId));
        } else {
            GetSubscriptionUseCase.SubscriptionDto dto = getSubscriptionUseCase.execute(SubscriptionId.of(subscriptionId));
            subscriptionDto = new SubscriptionDto(dto);
        }
        return subscriptionDto;
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/api/client/{clientId}/subscription/upsert")
    public ResponseEntity<Object> upsertAction(@RequestBody @Valid SubscriptionDto subscriptionDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            if (subscriptionDto.getId().equals(SubscriptionId.empty().getValue())) {
                CreateSubscriptionUseCase.SubscriptionDto dto = Mapper.toDomainDtoForCreate(subscriptionDto);
                createSubscriptionUseCase.execute(dto);
            } else {
                UpdateSubscriptionUseCase.SubscriptionDto dto = Mapper.toDomainDtoForUpdate(subscriptionDto);
                updateSubscriptionUseCase.execute(dto);
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
    @PostMapping({"/api/client/{clientId}/subscription/{subscriptionId}/delete"})
    public ResponseEntity<String> deleteAction(@PathVariable UUID clientId, @PathVariable UUID subscriptionId) {
        if (deleteSubscriptionUseCase.execute(ClientId.of(clientId), SubscriptionId.of(subscriptionId)))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Something wrong");
    }

}
