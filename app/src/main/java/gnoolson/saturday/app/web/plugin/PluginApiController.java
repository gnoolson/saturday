package gnoolson.saturday.app.web.plugin;

import gnoolson.saturday.app.plugin.PluginInfo;
import gnoolson.saturday.app.plugin.PluginService;
import gnoolson.saturday.app.web.plugin.dto.PluginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plugin")
public class PluginApiController {

    private final PluginService pluginService;

    /*
     *
     *
     * */
    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/all")
    public List<PluginDto> getPlugins() {
        List<PluginInfo> plugins = pluginService.getPlugins();

        List<PluginDto> result = plugins.stream().map((pluginInfoDto) -> {
            return new PluginDto(
                    pluginInfoDto.getId(),
                    pluginInfoDto.getProvider(),
                    pluginInfoDto.getVersion(),
                    pluginInfoDto.getStatus(),
                    pluginInfoDto.getDescription()
            );

        }).collect(Collectors.toList());

        return result;
    }

    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/{pluginId}/control")
    public void control(@PathVariable String pluginId, @RequestParam("flag") boolean flag) {
        pluginService.control(pluginId, flag);
    }


}
