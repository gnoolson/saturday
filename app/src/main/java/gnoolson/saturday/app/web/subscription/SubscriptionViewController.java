package gnoolson.saturday.app.web.subscription;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.EmptyId;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.SubscriptionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Controller
public class SubscriptionViewController {

    @GetMapping("/client/{clientId}/subscription/all")
    public String allSubscriptionPage(@PathVariable UUID clientId, Model model) {
        model.addAttribute("clientId", clientId);
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);

        return "subscription/all/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping({"/client/{clientId}/subscription/create", "/client/{clientId}/subscription/{subscriptionId}/edit"})
    public String upsertPage(@PathVariable UUID clientId, @PathVariable(required = false) UUID subscriptionId, Model model) {
        if (subscriptionId == null)
            subscriptionId = SubscriptionId.empty().getValue();

        model.addAttribute("clientId", clientId);
        model.addAttribute("subscriptionId", subscriptionId);
        model.addAttribute("emptyId", EmptyId.getValue());

        return "subscription/upsert/index";
    }

    @PreAuthorize("hasRole('EDITOR')")
    @GetMapping("/client/{clientId}/subscription/{subscriptionId}/delete")
    public String deletePage(@PathVariable UUID clientId, @PathVariable UUID subscriptionId, Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("subscriptionId", subscriptionId);
        return "subscription/delete/index";
    }

}
