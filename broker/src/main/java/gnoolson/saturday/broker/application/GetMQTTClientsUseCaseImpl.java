package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.port.inbount.GetConnectedMQTTClientsUseCase;
import gnoolson.saturday.broker.port.outbount.ConnectedClientsProviderGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetMQTTClientsUseCaseImpl implements GetConnectedMQTTClientsUseCase {

    private final ConnectedClientsProviderGateway connectedClientsProviderGateway;

    /*
     *
     *
     * */
    @Override
    public List<ClientDto> execute() {
        return connectedClientsProviderGateway.execute().stream()
                .map(dto -> new GetConnectedMQTTClientsUseCase.ClientDto(
                        dto.getName(),
                        dto.getUsername(),
                        dto.getConnected()
                )).collect(Collectors.toList());
    }

}
