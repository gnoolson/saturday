package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.ScriptId;


public interface ScriptControlUseCase {

    void execute(ScriptId id, boolean flag);

}
