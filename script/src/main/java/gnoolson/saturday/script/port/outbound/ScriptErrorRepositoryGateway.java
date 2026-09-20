package gnoolson.saturday.script.port.outbound;

import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptErrorId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.model.entity.ScriptError;

import java.util.List;

public interface ScriptErrorRepositoryGateway {

    ScriptErrorId save(ScriptError scriptError);

    PositiveNumber count(ScriptId scriptId);

    List<ScriptError> find(ScriptId scriptId);

    void deleteAll(ScriptId scriptId);

}
