package gnoolson.saturday.app.web.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ExceptionController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    /*
     *
     *
     * */
    @RequestMapping("/error")
    public String handleError(WebRequest request, Model model) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults()
                .including(ErrorAttributeOptions.Include.MESSAGE)
                .including(ErrorAttributeOptions.Include.EXCEPTION)
                .including(ErrorAttributeOptions.Include.STACK_TRACE)
                .including(ErrorAttributeOptions.Include.BINDING_ERRORS);

        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(request, options);

        model.addAllAttributes(errorDetails);
        return "error/index";
    }

}