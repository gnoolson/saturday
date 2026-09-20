package gnoolson.saturday.app.web.root;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.app.web.root.dto.DashboardDto;
import gnoolson.saturday.app.web.root.dto.DashboardMapper;
import gnoolson.saturday.app.web.root.dto.ProjectDto;
import gnoolson.saturday.app.web.root.dto.ProjectDtoMapper;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.port.inbound.GetAllDashboardsForRootPageUseCase;
import gnoolson.saturday.project.port.inbound.GetAllProjectsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/root")
public class RootApiController {

    private final GetAllDashboardsForRootPageUseCase getAllDashboardsForRootPageUseCase;
    private final GetAllProjectsUseCase getAllProjectsUseCase;

    /*
     *
     *
     * */
    @GetMapping(value = "/dashboards")
    public List<DashboardDto> getDashboardsData() {
        Role role = SecurityUtil.getRole();

        List<GetAllDashboardsForRootPageUseCase.DashboardDto> dashboards = getAllDashboardsForRootPageUseCase.execute(role);
        return DashboardMapper.toDto(dashboards);
    }

    @GetMapping(value = "/projects")
    public List<ProjectDto> getProjectsData() {

        List<GetAllProjectsUseCase.ProjectDto> projects = getAllProjectsUseCase.execute();
        return ProjectDtoMapper.toDto(projects);
    }

}
