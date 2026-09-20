package gnoolson.saturday.dashboard.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class DashboardResponse {

    private final Object value;

    /*
     *
     *
     * */
    public DashboardResponse(Object value) {
        ValueObjectValidator.checkNotNull(value, "DashboardResponse");
        this.value = value;
    }

    public static DashboardResponse of(Object value) {
        return new DashboardResponse(value);
    }

    public static DashboardResponse empty() {
        return new DashboardResponse(new Object());
    }

}
