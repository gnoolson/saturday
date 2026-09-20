package gnoolson.saturday.dashboard.model.vo;


import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Html {

    private final String value;

    /*
     *
     *
     * */
    public Html(String value) {
        ValueObjectValidator.checkNotNull(value, "Html"); // +
        this.value = value.trim();
    }

    public static Html of(String value) {
        return new Html(value);
    }

}
