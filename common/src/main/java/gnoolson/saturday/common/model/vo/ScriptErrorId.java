package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class ScriptErrorId implements Id {

    private final long value;

    /*
     *
     *
     * */
    private ScriptErrorId() {
        this.value = 0;
    }

    public ScriptErrorId(long value) {
        ValueObjectValidator.checkNotNull(value, "ScriptErrorId"); // +
        this.value = value;
    }

    public static ScriptErrorId of(long id) {
        return new ScriptErrorId(id);
    }

    public static ScriptErrorId empty() {
        return new ScriptErrorId();
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String getStringValue() {
        return "ScriptErrorId:" + value;
    }

}
