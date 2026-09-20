package gnoolson.saturday.app.web.login;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.user.port.inbound.GetNumberOfUsersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final GetNumberOfUsersUseCase getNumberOfUsersUseCase;

    @GetMapping("/login")
    public String login(Model model) {
        PositiveNumber numberOfUsers = getNumberOfUsersUseCase.execute();

        if (numberOfUsers.isZero()) {
            model.addAttribute("createEditor", true);
        } else {
            model.addAttribute("createEditor", false);
        }

        return "login/index";
    }


}