package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

@ToString
@EqualsAndHashCode
@Getter
public class ScheduleName {

    private final String value;

    public ScheduleName(String value) {
        ValueObjectValidator.checkNotNull(value, "name");
        this.value = StringUtils.capitalize(value);
    }

    public static ScheduleName of(String value) {
        return new ScheduleName(value);
    }

    public static ScheduleName generate() {
        final int length = 7;
        final boolean useLetters = true;
        final boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return ScheduleName.of("schedule_" + generatedString);
    }


}
