package gnoolson.saturday.app.web.plugin.dto;

import lombok.Data;

@Data
public class PluginDto {

    private final String id;
    private final String provider;
    private final String version;
    private final String status;
    private final String description;

}
