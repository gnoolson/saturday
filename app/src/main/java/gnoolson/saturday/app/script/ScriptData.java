package gnoolson.saturday.app.script;

import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.vo.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScriptData {

    private final Script script;
    private final List<Script> includedScripts;
    private final CachingTime cachingTime;

    @Getter
    @RequiredArgsConstructor
    public static class Script {
        private final ScriptName name;
        private final Code code;
    }

}