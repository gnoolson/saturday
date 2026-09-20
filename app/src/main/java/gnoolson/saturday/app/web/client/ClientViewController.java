package gnoolson.saturday.app.web.client;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.EmptyId;
import gnoolson.saturday.common.model.vo.Role;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Log4j2
@Controller
@RequestMapping("/client")
public class ClientViewController {

    @GetMapping("/all")
    public String allClientsPage(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);

        return "client/all/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/create", "/{id}/edit"})
    public String upsertClientPage(@PathVariable(required = false) UUID id, Model model) {
        if (id == null)
            id = ClientId.empty().getValue();

        model.addAttribute("id", id);
        model.addAttribute("emptyId", EmptyId.getValue());

        return "client/upsert/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/{id}/delete")
    public String deleteClientPage(Model model, @PathVariable UUID id) {
        model.addAttribute("id", id);

        return "client/delete/index";
    }

}
