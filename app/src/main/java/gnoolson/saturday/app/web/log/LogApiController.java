package gnoolson.saturday.app.web.log;

import gnoolson.saturday.app.script.log.SaturdayLog;
import gnoolson.saturday.app.web.script.dto.validation.RowsValidator;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/log")
public class LogApiController {

    private final SaturdayLog saturdayLog;

    /*
     *
     * */
    @GetMapping
    public String getLogText(@RequestParam(name = "rows", defaultValue = "1000") int rows) {
        RowsValidator.check(rows);
        return saturdayLog.getText(PositiveNumber.of(rows));
    }

}
