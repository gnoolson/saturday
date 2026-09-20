package gnoolson.saturday.app.web.schedule;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.EmptyId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.ScheduleId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/schedule")
public class ScheduleViewController {

    @GetMapping("/all")
    public String schedulesPage(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);

        return "schedule/all/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/create", "/{id}/edit"})
    public String upsertSchedulePage(@PathVariable(required = false) UUID id, Model model) {
        if (id == null)
            id = ScheduleId.empty().getValue();

        model.addAttribute("id", id);
        model.addAttribute("emptyId", EmptyId.getValue());

        return "schedule/upsert/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/{id}/delete"})
    public String deleteSchedulePage(Model model, @PathVariable UUID id) {
        model.addAttribute("id", id);
        return "schedule/delete/index";
    }

}
