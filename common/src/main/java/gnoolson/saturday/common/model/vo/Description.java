package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Description {

    private final String value;

    /*
     *
     *
     * */
    public Description(String value) {
        ValueObjectValidator.checkNotNull(value, "Description"); // +
        this.value = value;
    }

    public static Description empty() {
        return new Description("");
    }

    public static Description of(String value) {
        return new Description(value);
    }

}
