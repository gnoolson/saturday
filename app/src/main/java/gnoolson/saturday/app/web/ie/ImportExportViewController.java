package gnoolson.saturday.app.web.ie;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole('SUPER')")
@Controller
@RequestMapping("/ie")
public class ImportExportViewController {

    @GetMapping("/export")
    public String exportPage() {
        return "ie/export/index";
    }

    @GetMapping("/import")
    public String importPage() {
        return "ie/import/index";
    }

}
