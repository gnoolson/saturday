package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class ScheduleId implements Id {

    private final UUID value;

    /*
     *
     *
     * */
    private ScheduleId() {
        this.value = EmptyId.getValue();
    }

    public ScheduleId(UUID value) {
        ValueObjectValidator.checkNotNull(value, "ScheduleId"); // +
        this.value = value;
    }

    public static ScheduleId of(UUID value) {
        return new ScheduleId(value);
    }

    public static ScheduleId empty() {
        return new ScheduleId();
    }

    public boolean isEmpty() {
        return EmptyId.isEmpty(this.value);
    }

    @Override
    public String getStringValue() {
        return "schedule_id:" + value.toString();
    }

}
