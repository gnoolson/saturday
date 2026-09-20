package gnoolson.saturday.app.web.dashboard.dto;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.model.vo.Access;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.CreateDashboardUseCase;
import gnoolson.saturday.dashboard.port.inbound.GetAllDashboardsUseCase;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardUseCase;
import gnoolson.saturday.dashboard.port.inbound.UpdateDashboardUseCase;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardMapper {

    public static List<DashboardDto> toDto2(List<GetAllDashboardsUseCase.DashboardDto> domainDtoList) {
        return domainDtoList.stream().map(domainDto -> toDto(domainDto)).collect(Collectors.toList());
    }

    public static DashboardDto toDto(GetAllDashboardsUseCase.DashboardDto domainDto) {
        return new DashboardDto(
                domainDto.getId().getValue(),
                domainDto.getName().getValue(),
                domainDto.getProjectId().getValue(),
                domainDto.getDescription().getValue(),
                StringUtils.EMPTY,
                ScriptId.empty().getValue(),
                domainDto.getAccess().name()
        );
    }

    public static CreateDashboardUseCase.DashboardDto toDomainDtoForCreateUseCase(DashboardDto dashboardDto) {
        return new CreateDashboardUseCase.DashboardDto(
                ProjectId.of(dashboardDto.getProjectId()),
                DashboardName.of(dashboardDto.getName()),
                Html.of(dashboardDto.getHtml()),
                Description.of(dashboardDto.getDescription()),
                ScriptId.of(dashboardDto.getScriptId()),
                Access.valueOf(dashboardDto.getAccess())
        );
    }

    public static UpdateDashboardUseCase.DashboardDto toDomainDtoForUpdateUseCase(DashboardDto dashboardDto) {
        return new UpdateDashboardUseCase.DashboardDto(
                DashboardId.of(dashboardDto.getId()),
                ProjectId.of(dashboardDto.getProjectId()),
                DashboardName.of(dashboardDto.getName()),
                Html.of(dashboardDto.getHtml()),
                Description.of(dashboardDto.getDescription()),
                ScriptId.of(dashboardDto.getScriptId()),
                Access.valueOf(dashboardDto.getAccess())
        );
    }

    public static DashboardDto toDto(GetDashboardUseCase.DashboardDto domainDto) {
        return new DashboardDto(
                domainDto.getId().getValue(),
                domainDto.getName().getValue(),
                domainDto.getProjectId().getValue(),
                domainDto.getDescription().getValue(),
                domainDto.getHtml().getValue(),
                domainDto.getScriptId().getValue(),
                domainDto.getAccess().name()
        );
    }


}
