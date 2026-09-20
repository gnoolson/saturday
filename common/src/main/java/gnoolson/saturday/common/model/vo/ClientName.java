package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

@ToString
@EqualsAndHashCode
@Getter
public class ClientName {

    private final String value;

    /*
     *
     *
     * */
    public ClientName(String value) {
        ValueObjectValidator.checkNotNull(value, "ClientName");
        this.value = value;
    }

    public static ClientName of(String value) {
        return new ClientName(value);
    }

    public static ClientName random() {
        final int length = 7;
        final boolean useLetters = true;
        final boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return ClientName.of("saturday_" + generatedString);
    }

}
