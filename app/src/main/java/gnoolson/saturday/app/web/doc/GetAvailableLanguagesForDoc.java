package gnoolson.saturday.app.web.doc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetAvailableLanguagesForDoc {

    private final List<String> languages = new ArrayList<>();

    /*
     *
     *
     * */
    public GetAvailableLanguagesForDoc(@Value("${spring.thymeleaf.prefix}") String templatePath) {
        this.readResources(templatePath);
    }

    public List<String> execute() {
        return languages;
    }

    /*
     *
     *
     * */
    private void readResources(String templatePath) {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(templatePath + "/doc/*");

            for (Resource resource : resources) {
                File file = resource.getFile();
                if (file.isDirectory()) {
                    this.languages.add(file.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

}
