package gnoolson.saturday.app.repository.backup;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DeleteUnnecessaryBackupsImpl implements DeleteUnnecessaryBackups {

    private final File folder;
    private final PositiveNumber maxFiles;

    /*
     *
     *
     * */
    @Override
    public void execute() throws IOException {
        File[] files = folder.listFiles();
        if (files == null)
            return;

        if (files.length <= maxFiles.getValue())
            return;

        List<File> sortedFiles = Arrays.stream(files).sorted(Comparator.comparingLong((File f) -> f.lastModified())).collect(Collectors.toList());
        List<File> filesForDelete = sortedFiles.subList(0, files.length - maxFiles.getValue());

        for (File file : filesForDelete) {
            FileUtils.delete(file);
        }
    }

}
