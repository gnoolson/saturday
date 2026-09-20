package gnoolson.saturday.app.web.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/version")
public class VersionApiController {

    @Autowired
    private BuildProperties buildProperties;

    /*
     *
     *
     * */
    @GetMapping
    public String get() {
        return buildProperties.getVersion();
    }

}
