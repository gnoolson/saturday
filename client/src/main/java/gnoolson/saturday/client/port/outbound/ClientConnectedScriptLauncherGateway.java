package gnoolson.saturday.client.port.outbound;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ScriptId;


public interface ClientConnectedScriptLauncherGateway {

    void execute(ScriptId scriptId, ClientId clientId);

}
