package gnoolson.saturday.app.web;

import gnoolson.saturday.common.model.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalDataAdvice {

    @Autowired
    private BuildProperties buildProperties;

    /*
    *
    *
    * */
    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.SUPER);
        model.addAttribute("role", role.name());
        model.addAttribute("version", buildProperties.getVersion());
    }

}
