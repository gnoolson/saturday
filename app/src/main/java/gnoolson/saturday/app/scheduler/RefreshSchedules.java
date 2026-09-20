package gnoolson.saturday.app.scheduler;

import gnoolson.saturday.schedule.port.inbound.RefreshSchedulesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class RefreshSchedules {

    private final RefreshSchedulesUseCase refreshSchedulesUseCase;

    /*
     *
     *
     * */
    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void refreshActualSchedules() {
        try {
            refreshSchedulesUseCase.execute();
        } catch (Exception e) {
            log.warn("Exception", e);
        }
    }

}
