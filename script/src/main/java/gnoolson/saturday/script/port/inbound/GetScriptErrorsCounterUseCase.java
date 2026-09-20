package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptId;

public interface GetScriptErrorsCounterUseCase {

    PositiveNumber execute(ScriptId scriptId);

}
