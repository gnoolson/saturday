package gnoolson.saturday.app.scheduler;

import gnoolson.saturday.client.port.inbound.CheckDisconnectedClientsUseCase;
import gnoolson.saturday.client.port.inbound.RefreshEnabledClientsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ClientConnectionSchedule {

    private final RefreshEnabledClientsUseCase refreshEnabledClientsUseCase;
    private final CheckDisconnectedClientsUseCase checkDisconnectedClientsUseCase;

    /*
     *
     *
     * */
    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void refreshConnections() {
        try {
            refreshEnabledClientsUseCase.execute();
        } catch (Exception e) {
            log.warn("Exception", e);
        }
    }

    @Scheduled(fixedDelay = 500, initialDelay = 6000)
    public void checkConnectedClients() {
        try {
            checkDisconnectedClientsUseCase.execute();
        } catch (Exception e) {
            log.warn("Exception", e);
        }
    }

}
