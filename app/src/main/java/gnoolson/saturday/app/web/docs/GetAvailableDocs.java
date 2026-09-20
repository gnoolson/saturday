package gnoolson.saturday.app.web.docs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetAvailableDocs {

    private final List<String> docs = new ArrayList<>();

    /*
     *
     *
     * */
    public GetAvailableDocs(@Value("${spring.thymeleaf.prefix}") String templatePath) {
        this.readResources(templatePath);
    }

    public List<String> execute() {
        return docs;
    }

    /*
     *
     *
     * */
    private void readResources(String templatePath) {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(templatePath + "/docs/*");

            for (Resource resource : resources) {
                File file = resource.getFile();
                if (file.isDirectory()) {
                    this.docs.add(file.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e); // +
        }
    }

}
