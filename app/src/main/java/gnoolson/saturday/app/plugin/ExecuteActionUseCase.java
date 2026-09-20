package gnoolson.saturday.app.plugin;

import java.util.Map;
import java.util.Optional;

public interface ExecuteActionUseCase {

    Optional<Map<String, Object>> execute(String pluginId, Map<String, Object> requestBody);

}
