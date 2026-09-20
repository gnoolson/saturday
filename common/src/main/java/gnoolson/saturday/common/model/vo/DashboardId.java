package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
@Getter
public class DashboardId implements Id {

    private final UUID value;

    /*
     *
     *
     * */
    private DashboardId() {
        this.value = EmptyId.getValue();
    }

    public DashboardId(UUID value) {
        ValueObjectValidator.checkNotNull(value, "DashboardId"); // +
        this.value = value;
    }

    public static DashboardId of(UUID value) {
        return new DashboardId(value);
    }

    public static DashboardId empty() {
        return new DashboardId();
    }

    public boolean isEmpty() {
        return EmptyId.isEmpty(this.value);
    }

    @Override
    public String getStringValue() {
        return "dashboard_id:" + value.toString();
    }

}
