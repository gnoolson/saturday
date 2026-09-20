package gnoolson.saturday.export_import.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Path {

    private final String value;

    /*
     *
     *
     * */
    public Path(String value) {
        ValueObjectValidator.checkNotNull(value, "Path");
        this.value = value;
    }

    public static Path of(String value) {
        return new Path(value);
    }

}
