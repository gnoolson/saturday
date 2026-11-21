package gnoolson.saturday.app.scheduler;

import gnoolson.saturday.app.repository.backup.CreateBackup;
import gnoolson.saturday.app.repository.backup.DeleteUnnecessaryBackups;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class H2BackupSchedule {

    private final CreateBackup createBackup;
    private final boolean useBackup;
    private final DeleteUnnecessaryBackups deleteUnnecessaryBackups;

    /*
     *
     *
     * */
    public H2BackupSchedule(CreateBackup createBackup, @Value("${gnoolson.saturday.h2.backup.use}") boolean useBackup, DeleteUnnecessaryBackups deleteUnnecessaryBackups) {
        this.createBackup = createBackup;
        this.useBackup = useBackup;
        this.deleteUnnecessaryBackups = deleteUnnecessaryBackups;
    }

    @Scheduled(cron = "${gnoolson.saturday.h2.backup.cron}")
    public void refreshConnections() {
        try {
            if (!useBackup)
                return;

            createBackup.execute();
            deleteUnnecessaryBackups.execute();
        } catch (Exception e) {
            log.warn("Exception", e);
        }
    }

}
