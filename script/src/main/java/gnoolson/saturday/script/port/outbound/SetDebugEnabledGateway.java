package gnoolson.saturday.script.port.outbound;

import gnoolson.saturday.common.model.vo.ScriptId;

public interface SetDebugEnabledGateway {

    void execute(ScriptId scriptId, boolean flag);

}
