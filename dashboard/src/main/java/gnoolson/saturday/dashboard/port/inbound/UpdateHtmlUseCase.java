package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.dashboard.model.vo.Html;


public interface UpdateHtmlUseCase {

    void execute(DashboardId id, Html html);

}
