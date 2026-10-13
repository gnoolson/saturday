package gnoolson.saturday.app.web;

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
        model.addAttribute("version", buildProperties.getVersion());
    }

}
