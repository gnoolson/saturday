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
public class DashboardName {

    private final String value;

    /*
     *
     *
     * */
    public DashboardName(String value) {
        ValueObjectValidator.checkNotNull(value, "DashboardName"); // +
        this.value = StringUtils.capitalize(value);
    }

    public static DashboardName of(String value) {
        return new DashboardName(value);
    }

    public static DashboardName generate() {
        final int length = 7;
        final boolean useLetters = true;
        final boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return DashboardName.of("saturday_" + generatedString);
    }


}
