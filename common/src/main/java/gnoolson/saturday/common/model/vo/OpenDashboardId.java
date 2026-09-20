package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
@Getter
public class OpenDashboardId implements Id {

    private final UUID value;
    private final DashboardId dashboardId;

    /*
     *
     *
     * */
    private OpenDashboardId() {
        this.value = EmptyId.getValue();
        this.dashboardId = DashboardId.empty();
    }

    public OpenDashboardId(UUID value, DashboardId dashboardId) {
        ValueObjectValidator.checkNotNull(value, "OpenDashboardId"); // +
        this.value = value;
        this.dashboardId = dashboardId;
    }

    public static OpenDashboardId of(UUID value, DashboardId dashboardId) {
        return new OpenDashboardId(value, dashboardId);
    }

    public static OpenDashboardId empty() {
        return new OpenDashboardId();
    }

    public boolean isEmpty() {
        return EmptyId.isEmpty(this.value);
    }

    @Override
    public String getStringValue() {
        return "open_dashboard_id:" + value.toString();
    }

}
