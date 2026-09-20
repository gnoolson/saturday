package gnoolson.saturday.script.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Stack {

    private final String value;

    /*
     *
     *
     * */
    private Stack() {
        this.value = "";
    }

    public Stack(String value) {
        ValueObjectValidator.checkNotNull(value, "Stack"); // +
        this.value = value;
    }

    public static Stack empty() {
        return new Stack();
    }

    public static Stack of(String value) {
        return new Stack(value);
    }

}
