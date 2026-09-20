package gnoolson.saturday.app.web.docs;

import gnoolson.saturday.app.markdown.MarkdownService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
public class DocsController {

    public static final String DEFAULT_LANGUAGE = "en";
    private final GetAvailableDocs getAvailableDocs;
    private final MarkdownService markdownService;
    @Value("${spring.thymeleaf.prefix}")
    private String prefix;

    /*
     *
     *
     * */
    @GetMapping("/docs/**")
    public String index(HttpServletRequest request, Model model) throws IOException {
        Locale locale = LocaleContextHolder.getLocale();
        String language = locale.getLanguage();

        List<String> languages = this.getAvailableDocs.execute();
        if (!languages.contains(language))
            language = DEFAULT_LANGUAGE;

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        File file = ResourceUtils.getFile(pathToFile(path, language));

        String markdown = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String html = markdownService.toHtml(markdown);
        model.addAttribute("content", html);

        return "docs/index";
    }

    /*
     *
     *
     * */
    private String pathToFile(String path, String language) {
        String[] segments = path.split("/");

        String fullPath = prefix + "docs/" + language;
        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            if (segment.isEmpty() || segment.equals("docs"))
                continue;
            fullPath += "/" + segment;
        }
        fullPath += "/index.md";
        return fullPath;
    }


}
