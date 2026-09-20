package gnoolson.saturday.app.web.client.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AuthFieldsValidator.class)
@Documented
public @interface AuthFieldsValidation {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "";

}