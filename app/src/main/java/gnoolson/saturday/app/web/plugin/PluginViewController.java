package gnoolson.saturday.app.web.plugin;

import gnoolson.saturday.app.markdown.MarkdownService;
import gnoolson.saturday.app.plugin.ExecuteActionUseCase;
import gnoolson.saturday.app.plugin.GetContentUseCase;
import gnoolson.saturday_plugin_api.Content;
import gnoolson.saturday_plugin_api.MarkdownContent;
import gnoolson.saturday_plugin_api.ThymeleafContent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/plugin")
public class PluginViewController {

    private final MarkdownService markdownService;
    private final GetContentUseCase getContentUseCase;
    private final ExecuteActionUseCase executeActionUseCase;
    private final Set<String> markupSelectors = Collections.singleton("content");

    @Autowired
    @Qualifier("stringTemplateEngine")
    private SpringTemplateEngine stringTemplateEngine;

    /*
     *
     *
     * */
    @GetMapping("/all")
    @PreAuthorize("hasRole('EDITOR')")
    public String allPluginsPage() {
        return "plugin/all/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/{pluginId}/**")
    public String getContent(HttpServletRequest request, @PathVariable String pluginId, Model model, @RequestParam Map<String, String> reqParam) {
        Locale locale = LocaleContextHolder.getLocale();
        String language = locale.getLanguage();

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        Optional<Content> contentOpt = getContentUseCase.execute(pluginId, language, path, reqParam);
        model.addAttribute("pluginId", pluginId);

        boolean found = false;
        if (contentOpt.isPresent()) {
            Content content = contentOpt.get();

            String html;
            if (content instanceof MarkdownContent) {
                html = markdownService.toHtml(content.getValue());
            } else if (content instanceof ThymeleafContent) {
                ThymeleafContent thymeleafContent = (ThymeleafContent) content;

                Context context = new Context();
                context.setVariables(thymeleafContent.getModels());


                html = stringTemplateEngine.process(
                        thymeleafContent.getValue(),
                        markupSelectors,
                        context
                );
            } else {
                html = content.getValue();
            }

            model.addAttribute("html", html);
            found = true;
        }

        model.addAttribute("found", found);

        return "plugin/template/index";
    }

    @ResponseBody
    @PreAuthorize("hasRole('EDITOR')")
    @PostMapping("/{pluginId}")
    public ResponseEntity<Map<String, Object>> executeAction(@PathVariable String pluginId, @RequestBody Map<String, Object> requestBody) {
        Optional<Map<String, Object>> responseOpt = executeActionUseCase.execute(pluginId, requestBody);
        if (responseOpt.isPresent()) {
            return ResponseEntity.ok(responseOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

}
