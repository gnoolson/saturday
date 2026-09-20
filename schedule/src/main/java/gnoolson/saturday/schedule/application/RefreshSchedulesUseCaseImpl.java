package gnoolson.saturday.schedule.application;

import gnoolson.saturday.schedule.model.entity.Schedule;
import gnoolson.saturday.schedule.port.inbound.RefreshSchedulesUseCase;
import gnoolson.saturday.schedule.port.outbound.ScheduleRepositoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RefreshSchedulesUseCaseImpl implements RefreshSchedulesUseCase {

    private final ScheduleRepositoryGateway scheduleRepositoryGateway;
    private final ScheduleManager scheduleManager;

    /*
     *
     *
     * */
    @Override
    public void execute() {
        List<Schedule> enabledSchedules = scheduleRepositoryGateway.findEnabled();
        scheduleManager.syncEnabledSchedules(enabledSchedules);
    }

}
