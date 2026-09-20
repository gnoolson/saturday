package gnoolson.saturday.user.port.inbound;

import gnoolson.saturday.user.model.vo.Language;

import java.util.List;


public interface GetAllAvailableLanguagesUseCase {

    List<Language> execute();

}
