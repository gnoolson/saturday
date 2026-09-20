package gnoolson.saturday.script.port.inbound;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptId;

import java.util.Optional;


public interface GetLogTextUseCase {

    Optional<String> execute(ScriptId scriptId, PositiveNumber rows);

}
