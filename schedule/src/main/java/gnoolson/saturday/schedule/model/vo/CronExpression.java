package gnoolson.saturday.schedule.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class CronExpression {

    private final String value;

    public CronExpression(String value) {
        ValueObjectValidator.checkNotNull(value, "CronExpression");
        this.value = value;
    }

    public static CronExpression of(String value) {
        return new CronExpression(value);
    }

}
