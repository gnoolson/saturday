package gnoolson.saturday.app.repository.backup;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.transaction.TransactionStarter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


@RequiredArgsConstructor
public class CreateBackupImpl implements CreateBackup {

    private final File folder;
    private final H2Repository h2Repository;
    private final TimeProvider timeProvider;
    private final TransactionStarter transactionStarter;

    /*
     *
     *
     * */
    @Override
    public void execute() throws IOException {
        String pathToBackupFile = folder.getAbsolutePath() + File.separator + generateFileName();
        transactionStarter.doIt(() -> {
            h2Repository.createBackup(pathToBackupFile);
        });
    }

    /*
     *
     *
     * */
    private String generateFileName() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeProvider.now().getValue());

        return String.format("saturday_mv_db_%d%s%s_%s%s%s.zip", calendar.get(Calendar.YEAR), addZeroIfNeeds(calendar.get(Calendar.MONTH)), addZeroIfNeeds(calendar.get(Calendar.DAY_OF_MONTH)), addZeroIfNeeds(calendar.get(Calendar.HOUR_OF_DAY)), addZeroIfNeeds(calendar.get(Calendar.MINUTE)), addZeroIfNeeds(calendar.get(Calendar.SECOND)));
    }

    private String addZeroIfNeeds(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }

}
