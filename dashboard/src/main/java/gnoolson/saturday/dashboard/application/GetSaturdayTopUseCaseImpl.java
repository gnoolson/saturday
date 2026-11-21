package gnoolson.saturday.dashboard.application;

import gnoolson.saturday.dashboard.model.vo.Html;
import gnoolson.saturday.dashboard.port.inbound.GetSaturdayTopUseCase;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RequiredArgsConstructor
public class GetSaturdayTopUseCaseImpl implements GetSaturdayTopUseCase {

    private final HtmlProvider htmlProvider;

    /*
     *
     *
     * */
    @Override
    public Html execute() {
        return htmlProvider.execute();
    }

}
