package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.ScriptId;

public interface SetDebugEnabledUseCase {

    void execute(ScriptId id, boolean flag);

}
