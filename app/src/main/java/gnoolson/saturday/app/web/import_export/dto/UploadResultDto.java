package gnoolson.saturday.app.web.import_export.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class UploadResultDto {

    private final String fileName;
    private final String path;
    private final List<ProjectDto> projects;

    @RequiredArgsConstructor
    @Getter
    public static class ProjectDto {
        private final UUID id;
        private final String name;
        private final boolean alreadyExists;
    }

}
