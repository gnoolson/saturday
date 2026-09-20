package gnoolson.saturday.app.web.import_export.dto;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.export_import.model.vo.Path;
import gnoolson.saturday.export_import.port.inbound.ExportUseCase;
import gnoolson.saturday.export_import.port.inbound.ImportUseCase;
import gnoolson.saturday.export_import.port.inbound.UploadImportFileUseCase;

import java.util.Set;
import java.util.stream.Collectors;

public class DtoMapper {

    public static ExportUseCase.ExportProjectsDto toDomainDto(ExportProjectsDto exportProjectsDto) {
        Set<ProjectId> projectIdSet = exportProjectsDto.getProjectIdSet().stream().map(ProjectId::of).collect(Collectors.toSet());
        return new ExportUseCase.ExportProjectsDto(projectIdSet);
    }

    public static UploadResultDto toDto(UploadImportFileUseCase.UploadResultDto result) {
        return new UploadResultDto(
                result.getFileName().getValue(),
                result.getPath().getValue(),
                result.getProjects().stream().map(project -> {
                    return new UploadResultDto.ProjectDto(
                            project.getId().getValue(),
                            project.getName().getValue(),
                            project.isAlreadyExists()
                    );
                }).collect(Collectors.toList())
        );
    }

    public static ImportUseCase.ImportProjectsDto toDomainDto(ImportProjectsDto importProjectsDto) {
        return new ImportUseCase.ImportProjectsDto(
                Path.of(importProjectsDto.getPath()),
                importProjectsDto.getProjectIdSet().stream().map(projectId -> {
                    return ProjectId.of(projectId);
                }).collect(Collectors.toSet())
        );
    }

}
