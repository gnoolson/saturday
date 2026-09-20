package gnoolson.saturday.user.application;


import gnoolson.saturday.user.model.vo.Language;
import gnoolson.saturday.user.port.inbound.GetAllAvailableLanguagesUseCase;
import gnoolson.saturday.user.port.outbound.GetAllAvailableLanguagesGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllAvailableLanguagesUseCaseImpl implements GetAllAvailableLanguagesUseCase {

    private final GetAllAvailableLanguagesGateway getAllAvailableLanguagesGateway;
    private List<Language> languages;

    /*
     *
     *
     * */
    @Override
    public List<Language> execute() {
        if (this.languages == null)
            this.languages = this.getAllAvailableLanguagesGateway.execute();

        return this.languages;
    }

}
