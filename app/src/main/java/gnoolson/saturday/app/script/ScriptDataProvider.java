package gnoolson.saturday.app.script;

import gnoolson.saturday.common.model.vo.ScriptId;

public interface ScriptDataProvider {

    ScriptData execute(ScriptId scriptId);

}
