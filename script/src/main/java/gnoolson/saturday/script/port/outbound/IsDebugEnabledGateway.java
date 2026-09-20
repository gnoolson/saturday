package gnoolson.saturday.script.port.outbound;

import gnoolson.saturday.common.model.vo.ScriptId;

public interface IsDebugEnabledGateway {

    boolean execute(ScriptId id);

}
