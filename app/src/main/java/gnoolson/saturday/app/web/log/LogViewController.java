package gnoolson.saturday.app.web.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogViewController {

    @GetMapping
    public String index() {
        return "log/index";
    }

}
