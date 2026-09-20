package gnoolson.saturday.app.plugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PluginInfo {

    private final String id;
    private final String provider;
    private final String version;
    private final String status;
    private final String description;

}
