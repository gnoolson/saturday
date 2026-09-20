package gnoolson.saturday.lua_script_executor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScriptData {

    private final Script script;
    private final List<Script> includedScripts;

}