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
public class ScriptName {

    private final String value;

    /*
     *
     *
     * */
    public ScriptName(String value) {
        ValueObjectValidator.checkNotNull(value, "ScriptName"); // +
        this.value = StringUtils.capitalize(value);
    }

    public static ScriptName of(String value) {
        return new ScriptName(value);
    }

    public static ScriptName generate() {
        final int length = 7;
        final boolean useLetters = true;
        final boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return ScriptName.of("script_" + generatedString);
    }

}
