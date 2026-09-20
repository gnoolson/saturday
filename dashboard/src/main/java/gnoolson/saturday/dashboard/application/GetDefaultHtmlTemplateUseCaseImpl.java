package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.GetDefaultHtmlTemplateUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDefaultHtmlTemplateUseCaseImpl implements GetDefaultHtmlTemplateUseCase {

    private final Html html;

    /*
     *
     *
     * */
    @Override
    public Html execute() {
        return html;
    }

}
