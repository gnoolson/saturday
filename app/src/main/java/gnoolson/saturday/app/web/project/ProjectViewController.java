package gnoolson.saturday.app.web.project;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.Role;
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
@RequestMapping("/project")
public class ProjectViewController {


    @GetMapping("/all")
    public String projectsPage(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);

        return "project/all/index";
    }


    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/create", "/{id}/edit"})
    public String upsertPage(@PathVariable(required = false) UUID id, Model model) {
        if (id == null)
            id = ProjectId.empty().getValue();

        model.addAttribute("id", id);
        model.addAttribute("emptyId", ProjectId.empty().getValue());
        return "project/upsert/index";
    }


    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/{id}/delete"})
    public String deletePage(@PathVariable UUID id, Model model) {
        model.addAttribute("id", id);
        return "project/delete/index";
    }

}
