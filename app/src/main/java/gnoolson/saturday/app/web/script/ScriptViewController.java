package gnoolson.saturday.app.web.script;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.common.model.vo.ScriptId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/script")
public class ScriptViewController {

    @Value("${gnoolson.saturday.script.max_errors}")
    private int maxNumberOfErrors;

    /*
    *
    *
    * */
    @GetMapping("/all")
    public String scriptsPage(Model model) {
         return "script/all/index";
    }

    @PreAuthorize("hasRole('SUPER')")
    @GetMapping({"/create", "/{id}/edit"})
    public String upsertPage(@PathVariable(required = false) UUID id, Model model) {
        if (id == null)
            id = ScriptId.empty().getValue();

        model.addAttribute("id", id);
        model.addAttribute("emptyId", ScriptId.empty().getValue());
        return "script/upsert/index";
    }

    @PreAuthorize("hasRole('SUPER')")
    @GetMapping({"/{id}/code"})
    public String codePage(@PathVariable UUID id, Model model) {
        model.addAttribute("id", id);
        return "script/code/index";
    }

    @PreAuthorize("hasRole('SUPER')")
    @GetMapping({"/{id}/delete"})
    public String deletePage(@PathVariable UUID id, Model model) {
        model.addAttribute("id", id);
        return "script/delete/index";
    }

    @GetMapping({"/{id}/errors"})
    public String errorsPage(@PathVariable UUID id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("maxNumberOfErrors", maxNumberOfErrors);
        return "script/errors/index";
    }

    @GetMapping({"/{id}/log"})
    public String logPage(@PathVariable UUID id, Model model) {
        model.addAttribute("id", id);

        return "script/log/index";
    }

}
