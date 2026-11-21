package gnoolson.saturday.dashboard.model.vo;

import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class DashboardHtml {

    private final Html html;
    private final OpenDashboardId openDashboardId;

    /*
     *
     *
     * */
    public DashboardHtml(Html html, OpenDashboardId openDashboardId) {
        ValueObjectValidator.checkNotNull(html, "Html"); // +
        ValueObjectValidator.checkNotNull(openDashboardId, "OpenDashboardId"); // +

        this.html = html;
        this.openDashboardId = openDashboardId;
    }

    public static DashboardHtml of(Html html, OpenDashboardId openDashboardId) {
        return new DashboardHtml(html, openDashboardId);
    }

}
