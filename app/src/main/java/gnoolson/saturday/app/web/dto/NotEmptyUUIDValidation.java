package gnoolson.saturday.app.web.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyUUIDValidator.class)
@Documented
public @interface NotEmptyUUIDValidation {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "";

}