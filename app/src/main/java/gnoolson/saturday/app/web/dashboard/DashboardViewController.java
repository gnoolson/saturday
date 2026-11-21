package gnoolson.saturday.app.web.dashboard;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.EmptyId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.exception.NotAuthorized;
import gnoolson.saturday.dashboard.model.vo.DashboardHtml;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardHtmlUseCase;
import gnoolson.saturday.dashboard.port.inbound.GetSaturdayTopUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/dashboard")
public class DashboardViewController {

    private final GetDashboardHtmlUseCase getDashboardHtmlUseCase;
    private final GetSaturdayTopUseCase getSaturdayTopUseCase;

    @Autowired
    @Qualifier("stringTemplateEngine")
    private final SpringTemplateEngine stringTemplateEngine;

    /*
     *
     *
     * */
    @GetMapping("/all")
    public String dashboardsPage(Model model) {
        return "dashboard/all/index";
    }

    @ResponseBody
    @GetMapping(value = {"/open/{id}"}, produces = "text/html;charset=UTF-8")
    public ResponseEntity<?> openDashboardPage(@PathVariable UUID id) throws Exception {
        try {
            DashboardHtml dashboardHtml = getDashboardHtmlUseCase.execute(DashboardId.of(id), SecurityUtil.getRole());
            Html html = getSaturdayTopUseCase.execute();

            Context context = new Context();
            context.setLocale(LocaleContextHolder.getLocale());
            context.setVariable("dashboardId", dashboardHtml.getOpenDashboardId().getDashboardId().getValue());
            context.setVariable("openDashboardId", dashboardHtml.getOpenDashboardId().getValue());
            context.setVariable("top", html.getValue());

            String template = stringTemplateEngine.process(
                    dashboardHtml.getHtml().getValue(),
                    context
            );

            return ResponseEntity.ok(template);
        } catch (NotAuthorized e) {
            if (log.isDebugEnabled())
                log.debug("NotAuthorized", e);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/"));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }
    }

    @PreAuthorize("hasRole('SUPER')")
    @GetMapping({"/create", "/{id}/edit"})
    public String upsertDashboardPage(@PathVariable(required = false) UUID id, Model model) {
        if (id == null)
            id = DashboardId.empty().getValue();

        model.addAttribute("id", id);
        model.addAttribute("emptyId", EmptyId.getValue());

        return "dashboard/upsert/index";
    }

    @PreAuthorize("hasRole('SUPER')")
    @GetMapping({"/{id}/code"})
    public String codePage(@PathVariable UUID id, Model model) {
        model.addAttribute("id", id);
        return "dashboard/code/index";
    }

    @PreAuthorize("hasRole('SUPER')")
    @GetMapping({"/{id}/delete"})
    public String deleteDashboardPage(Model model, @PathVariable UUID id) {
        model.addAttribute("id", id);
        return "dashboard/delete/index";
    }

    /*
    *
    *
    * */
    @RequiredArgsConstructor
    public static class ThymeleafMessageWrapper {
        private final MessageSource messageSource;
        private final java.util.Locale locale;

        /*
        *
        *
        * */
        public String get(String key) {
            return messageSource.getMessage(key, null, "???" + key + "???", locale);
        }

        public String get(String key, Object... args) {
            return messageSource.getMessage(key, args, "???" + key + "???", locale);
        }
    }
}
