package gnoolson.saturday.app.web.login;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.Role;
import gnoolson.saturday.user.port.inbound.GetNumberOfUsersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginViewController {

    private final GetNumberOfUsersUseCase getNumberOfUsersUseCase;

    /*
     *
     *
     * */
    @GetMapping
    public String index(Model model) {
        Role role = SecurityUtil.getRole();
        if (role != Role.ANONYMOUS)
            return "redirect:/";

        PositiveNumber numberOfUsers = getNumberOfUsersUseCase.execute();
        if (numberOfUsers.isZero())
            return "redirect:/create-super";

        return "login/index";
    }

}