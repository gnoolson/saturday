package gnoolson.saturday.app.web.broker;

import gnoolson.saturday.app.web.SecurityUtil;
import gnoolson.saturday.common.model.vo.Role;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/mqtt-broker")
public class MQTTBrokerViewController {

    @GetMapping
    public String indexPage(Model model) {
        Role role = SecurityUtil.getRole();
        model.addAttribute("superAccess", role == Role.EDITOR);

        return "mqtt_broker/index";
    }

}
