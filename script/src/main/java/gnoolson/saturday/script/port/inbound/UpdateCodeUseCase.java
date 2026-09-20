package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.model.vo.Code;

import java.util.List;

public interface UpdateCodeUseCase {

    void execute(ScriptId scriptId, Code code, List<ScriptId> includedScripts);

}
