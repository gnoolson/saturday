package gnoolson.saturday.app.web.import_export;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole('EDITOR')")
@Controller
@RequestMapping("/ie")
public class ImportExportViewController {

    @GetMapping("/export")
    public String exportPage() {
        return "import_export/export/index";
    }

    @GetMapping("/import")
    public String importPage() {
        return "import_export/import/index";
    }

}
