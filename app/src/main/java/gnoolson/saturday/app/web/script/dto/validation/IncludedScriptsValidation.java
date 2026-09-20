package gnoolson.saturday.app.web.script.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IncludedScriptsValidator.class)
@Documented
public @interface IncludedScriptsValidation {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "";

}