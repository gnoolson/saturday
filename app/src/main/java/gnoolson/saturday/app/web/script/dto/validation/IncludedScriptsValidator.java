package gnoolson.saturday.app.web.script.dto.validation;

import gnoolson.saturday.common.model.vo.EmptyId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class IncludedScriptsValidator implements ConstraintValidator<IncludedScriptsValidation, List<UUID>> {

    @Override
    public boolean isValid(List<UUID> values, ConstraintValidatorContext context) {
        Map<UUID, Integer> counter = new HashMap<>();

        for (UUID value : values) {
            Integer quantity = counter.getOrDefault(value, 0);
            quantity++;
            if (quantity > 1)
                return false;

            if (EmptyId.isEmpty(value))
                return false;

            counter.put(value, quantity);
        }

        return true;
    }

}