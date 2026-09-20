package gnoolson.saturday.app.web.user;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.user.model.vo.Language;
import gnoolson.saturday.user.port.inbound.GetAllAvailableLanguagesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserViewController {

    private final GetAllAvailableLanguagesUseCase getAllAvailableLanguagesUseCase;

    /*
     *
     *
     * */
    @GetMapping("/all")
    public String usersPage(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);
        return "user/all/index";
    }

    @GetMapping("/language")
    public String languagePage(Model model, Principal principal) {
        List<String> languages = getAllAvailableLanguagesUseCase.execute().stream().map(Language::getValue).collect(Collectors.toList());
        model.addAttribute("languages", languages);

        return "user/language/index";
    }

}
