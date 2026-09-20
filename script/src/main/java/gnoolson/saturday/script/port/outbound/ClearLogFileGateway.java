package gnoolson.saturday.script.port.outbound;

import gnoolson.saturday.common.model.vo.ScriptId;


public interface ClearLogFileGateway {

    boolean execute(ScriptId id);

}
