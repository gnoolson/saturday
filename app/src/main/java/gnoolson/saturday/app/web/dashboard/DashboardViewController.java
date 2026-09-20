package gnoolson.saturday.app.web.dashboard;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.EmptyId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.dashboard.model.exception.NotAuthorized;
import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.GetDashboardHtmlUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/dashboard")
public class DashboardViewController {

    private final GetDashboardHtmlUseCase getDashboardHtmlUseCase;

    /*
     *
     *
     * */
    @GetMapping("/all")
    public String dashboardsPage(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);

        return "dashboard/all/index";
    }

    @ResponseBody
    @GetMapping(value = {"/{id}"}, produces = "text/html;charset=UTF-8")
    public ResponseEntity<?> openDashboardPage(@PathVariable UUID id) {
        try {
            Html html = getDashboardHtmlUseCase.execute(DashboardId.of(id), SecurityUtil.getRole());
            return ResponseEntity.ok(html.getValue());
        } catch (NotAuthorized e) {
            if (log.isDebugEnabled())
                log.debug("NotAuthorized", e);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/"));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/create", "/{id}/edit"})
    public String upsertDashboardPage(@PathVariable(required = false) UUID id, Model model) {
        if (id == null)
            id = DashboardId.empty().getValue();

        model.addAttribute("id", id);
        model.addAttribute("emptyId", EmptyId.getValue());

        return "dashboard/upsert/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/{id}/dev"})
    public String devPage(@PathVariable UUID id, Model model) {
        model.addAttribute("id", id);
        return "dashboard/dev/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/{id}/delete"})
    public String deleteDashboardPage(Model model, @PathVariable UUID id) {
        model.addAttribute("id", id);
        return "dashboard/delete/index";
    }

}
