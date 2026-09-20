package gnoolson.saturday.app.web.root.dto;

import gnoolson.saturday.dashboard.port.inbound.GetAllDashboardsForRootPageUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardMapper {

    public static List<DashboardDto> toDto(List<GetAllDashboardsForRootPageUseCase.DashboardDto> domainDtoList) {
        return domainDtoList.stream().map((domainDto) -> {
            return new DashboardDto(
                    domainDto.getId().getValue(),
                    domainDto.getName().getValue(),
                    domainDto.getProjectId().getValue(),
                    domainDto.getDescription().getValue()
            );
        }).collect(Collectors.toList());
    }

}
