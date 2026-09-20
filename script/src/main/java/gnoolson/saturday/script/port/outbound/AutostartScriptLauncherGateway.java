package gnoolson.saturday.script.port.outbound;

import gnoolson.saturday.common.model.vo.ScriptId;

public interface AutostartScriptLauncherGateway {

    void execute(ScriptId scriptId);

}
