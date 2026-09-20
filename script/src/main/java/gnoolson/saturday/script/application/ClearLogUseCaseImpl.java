package gnoolson.saturday.script.application;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.port.inbound.ClearLogFileUseCase;
import gnoolson.saturday.script.port.outbound.ClearLogFileGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ClearLogUseCaseImpl implements ClearLogFileUseCase {

    private final ClearLogFileGateway clearLogFileGateway;

    /*
     *
     *
     * */
    @Override
    public boolean execute(ScriptId scriptId) {
        return clearLogFileGateway.execute(scriptId);
    }

}
