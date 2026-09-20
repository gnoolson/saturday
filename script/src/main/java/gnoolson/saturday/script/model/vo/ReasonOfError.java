package gnoolson.saturday.script.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class ReasonOfError {

    private final String value;

    /*
     *
     *
     * */
    private ReasonOfError() {
        this.value = "";
    }

    public ReasonOfError(String value) {
        ValueObjectValidator.checkNotNull(value, "ReasonOfError"); // +
        this.value = value;
    }

    public static ReasonOfError empty() {
        return new ReasonOfError();
    }

    public static ReasonOfError of(String value) {
        return new ReasonOfError(value);
    }

}
