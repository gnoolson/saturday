package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
@Getter
public class ScriptId implements Id {

    private final UUID value;

    /*
     *
     *
     * */
    private ScriptId() {
        this.value = EmptyId.getValue();
    }

    public ScriptId(UUID value) {
        ValueObjectValidator.checkNotNull(value, "ScriptId"); // +
        this.value = value;
    }

    public static ScriptId of(UUID id) {
        return new ScriptId(id);
    }

    public static ScriptId empty() {
        return new ScriptId();
    }

    public static ScriptId random() {
        return of(UUID.randomUUID());
    }

    public boolean isEmpty() {
        return EmptyId.isEmpty(this.value);
    }

    @Override
    public String getStringValue() {
        return "script_id:" + value.toString();
    }

}
