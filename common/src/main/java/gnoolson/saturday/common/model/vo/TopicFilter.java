package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TopicFilter {

    private final String value;

    /*
     *
     *
     * */
    public TopicFilter(String value) {
        ValueObjectValidator.checkNotNull(value, "TopicFilter"); // +
        this.value = value;
    }

    public static TopicFilter of(String value) {
        return new TopicFilter(value);
    }

}
