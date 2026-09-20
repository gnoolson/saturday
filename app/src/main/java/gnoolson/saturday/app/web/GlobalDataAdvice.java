package gnoolson.saturday.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalDataAdvice {


    @ModelAttribute
    public void addGlobalAttributes(Model model) {

    }

}
