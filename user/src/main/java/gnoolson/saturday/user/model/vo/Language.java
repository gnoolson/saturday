package gnoolson.saturday.user.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Language {

    private final String value;

    /*
     *
     *
     * */
    public Language(String value) {
        ValueObjectValidator.checkNotNull(value, "Language");
        this.value = value.trim();
    }

    public static Language of(String value) {
        return new Language(value);
    }

    public static Language defaultValue() {
        return Language.of("en");
    }

}
