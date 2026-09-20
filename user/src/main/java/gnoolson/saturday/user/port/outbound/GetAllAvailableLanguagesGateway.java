package gnoolson.saturday.user.port.outbound;


import gnoolson.saturday.user.model.vo.Language;

import java.util.List;

public interface GetAllAvailableLanguagesGateway {

    List<Language> execute();

}
