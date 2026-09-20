package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Topic {

    private final String value;

    /*
     *
     *
     * */
    public Topic(String value) {
        ValueObjectValidator.checkNotNull(value, "Topic"); // +
        this.value = value;
    }

    public static Topic of(String value) {
        return new Topic(value);
    }

}
