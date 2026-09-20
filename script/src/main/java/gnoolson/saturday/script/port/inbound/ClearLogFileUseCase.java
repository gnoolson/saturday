package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.ScriptId;


public interface ClearLogFileUseCase {

    boolean execute(ScriptId scriptId);

}
