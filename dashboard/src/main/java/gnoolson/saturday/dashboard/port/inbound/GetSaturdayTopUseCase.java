package gnoolson.saturday.dashboard.port.inbound;

import gnoolson.saturday.dashboard.model.vo.Html;

public interface GetSaturdayTopUseCase {

    Html execute();

    /*
    *
    *
    * */
    interface HtmlProvider {
        Html execute();
    }

}
