package gnoolson.saturday.app.web.project;

import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.project.dto.ProjectDto;
import gnoolson.saturday.app.web.project.dto.ProjectMapper;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.project.port.inbound.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/project")
public class ProjectApiController {

    private final GetAllProjectsUseCase getAllProjectsUseCase;
    private final GetProjectUseCase getProjectUseCase;
    private final CreateProjectUseCase createProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    /*
     *
     *
     *
     * */
    @GetMapping("/all")
    public Collection<ProjectDto> getAllProjectsData() {
        List<GetAllProjectsUseCase.ProjectDto> projects = getAllProjectsUseCase.execute();
        return ProjectMapper.toDto(projects);
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/{id}")
    public ProjectDto getProjectData(@PathVariable UUID id) {
        ProjectDto projectDto;

        if (id.equals(ProjectId.empty().getValue())) {
            projectDto = new ProjectDto();
        } else {
            GetProjectUseCase.ProjectDto project = getProjectUseCase.execute(ProjectId.of(id));
            projectDto = new ProjectDto(project);
        }

        return projectDto;
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/upsert")
    public ResponseEntity<Object> upsertAction(@RequestBody @Valid ProjectDto projectDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        try {
            if (projectDto.getId().equals(ProjectId.empty().getValue())) {
                CreateProjectUseCase.ProjectDto project = ProjectMapper.toDomainDtoForCreateUseCase(projectDto);
                createProjectUseCase.execute(project);
            } else {
                UpdateProjectUseCase.ProjectDto project = ProjectMapper.toDomainForUpdateUseCase(projectDto);
                updateProjectUseCase.execute(project);
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
    @PostMapping({"/{id}/delete"})
    public ResponseEntity<String> deleteAction(@PathVariable UUID id) {
        if (deleteProjectUseCase.execute(ProjectId.of(id)))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Something wrong");
    }

}
