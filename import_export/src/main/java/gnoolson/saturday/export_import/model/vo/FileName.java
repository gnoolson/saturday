package gnoolson.saturday.export_import.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class FileName {

    private final String value;

    /*
     *
     *
     * */
    public FileName(String value) {
        ValueObjectValidator.checkNotNull(value, "FileName");
        this.value = value;
    }

    public static FileName of(String value) {
        return new FileName(value);
    }

}
