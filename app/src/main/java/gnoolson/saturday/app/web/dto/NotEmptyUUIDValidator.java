package gnoolson.saturday.app.web.dto;

import gnoolson.saturday.common.model.vo.EmptyId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class NotEmptyUUIDValidator implements ConstraintValidator<NotEmptyUUIDValidation, UUID> {

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
        if (EmptyId.isEmpty(uuid)) {
            return false;
        }
        return true;
    }

}