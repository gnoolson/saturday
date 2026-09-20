package gnoolson.saturday.app.web.schedule.dto.validation;

import org.springframework.scheduling.support.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CronExpressionValidator implements ConstraintValidator<CronExpressionValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean valid = CronExpression.isValidExpression(value);
        return valid;
    }

}