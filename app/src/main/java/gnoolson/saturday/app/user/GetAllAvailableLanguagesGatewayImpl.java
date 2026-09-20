package gnoolson.saturday.app.user;

import gnoolson.saturday.user.model.vo.Language;
import gnoolson.saturday.user.port.outbound.GetAllAvailableLanguagesGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetAllAvailableLanguagesGatewayImpl implements GetAllAvailableLanguagesGateway {

    private final String pathToMessages;

    /*
     *
     *
     * */
    public GetAllAvailableLanguagesGatewayImpl(@Value("${gnoolson.saturday.web.messages.path}") String pathToMessages) {
        this.pathToMessages = pathToMessages;
    }

    @Override
    public List<Language> execute() {
        try {
            File file = ResourceUtils.getFile(pathToMessages);

            if (!file.exists())
                throw new RuntimeException( // +
                        "Message directory was not found: " + pathToMessages
                );

            if (!file.isDirectory())
                throw new RuntimeException( // +
                        "Message path is not a directory: " + pathToMessages
                );

            String[] list = file.list();
            if (list == null)
                throw new RuntimeException( // +
                        "Failed to list files in message directory: " + pathToMessages
                );

            List<Language> result = new ArrayList<>();
            for (String s : list) {
                String language = s.replace("messages_", "").replace(".properties", "");
                result.add(Language.of(language));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); // +
        }
    }

}
