package gnoolson.saturday.app.web.schedule.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronExpressionValidator.class)
@Documented
public @interface CronExpressionValidation {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "";

}