package gnoolson.saturday.app.web.root;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Log4j2
@Controller
public class RootViewController {

    @GetMapping("/")
    public String rootPage() {
        return "root/index";
    }

}
