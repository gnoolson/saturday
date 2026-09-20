package gnoolson.saturday.script.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Code {

    private final String value;

    /*
     *
     *
     * */
    public Code(String value) {
        ValueObjectValidator.checkNotNull(value, "Code");
        this.value = value.trim();
    }

    public static Code of(String value) {
        return new Code(value);
    }

}
