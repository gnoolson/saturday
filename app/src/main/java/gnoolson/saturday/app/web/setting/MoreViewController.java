package gnoolson.saturday.app.web.setting;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/more")
public class MoreViewController {


    @GetMapping
    public String settingPage(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);

        return "more/index";
    }

}
