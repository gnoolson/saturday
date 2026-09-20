package gnoolson.saturday.lua_script_executor;

import gnoolson.saturday.lua_script_executor.lib.*;
import lombok.Getter;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseOsLib;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class Sandbox {

    @Getter
    private final String scriptName;
    @Getter
    private final Globals globals;
    @Getter
    private final LuaValue mainChunk;
    private final List<LuaLib> libs;
    private final List<Script> includedScripts;
    private boolean includedScriptsLoaded;

    /*
     *
     *
     * */
    public Sandbox(ScriptData scriptData, boolean fullAccess, List<LuaLib> libs, File libFolder) {
        this.libs = libs;
        this.includedScripts = scriptData.getIncludedScripts();

        this.scriptName = scriptData.getScript().getName();
        this.globals = new Globals();


        loadLuajLibs(fullAccess, libFolder);
        loadSaturdayLibs();

        LoadState.install(globals);
        LuaC.install(this.globals);

        this.mainChunk = this.globals.load(scriptData.getScript().getCode(), scriptData.getScript().getName());
    }

    public void release() {
        for (LuaLib luaLib : libs) {
            luaLib.release();
        }
    }

    public void setupFunctionalities(Collection<Functionality> functionalities) {
        for (Functionality functionality : functionalities) {
            setupFunctionalities(functionality);
        }
    }

    public void executeScript() {
        if (!includedScriptsLoaded) {
            includedScriptsLoaded = true;
            loadIncludedScripts();
        }
        mainChunk.call();
    }

    /*
     *
     *
     * */
    private void loadLuajLibs(boolean fullAccess, File libFolder) {
        globals.load(new BaseLib());
        globals.load(new PackageLib());
        globals.load(new StringLib());
        globals.load(new MathLib());
        globals.load(new TableLib());
        globals.load(new CoroutineLib());

        globals.get("package").set("path", LuaValue.valueOf(libFolder.getAbsolutePath() + "/?.lua;?.lua")); // ?

        globals.finder = new org.luaj.vm2.lib.ResourceFinder() {
            public java.io.InputStream findResource(String name) {
                try {
                    return new java.io.FileInputStream(name);
                } catch (java.io.FileNotFoundException e) {
                    return null;
                }
            }
        };

        if (fullAccess) {
            globals.load(new JseOsLib());
            globals.load(new JseIoLib());
        }
    }

    private void setupFunctionalities(Functionality functionality) {
        for (LuaLib luaLib : libs) {
            if (luaLib.getId().equals(functionality.getLuaLibId())) {
                luaLib.updateFunctionality(functionality.getFunctionalityInstance());
                return;
            }
        }
    }

    private void loadIncludedScripts() {
        for (Script script : includedScripts) {
            LuaValue chunk = this.globals.load(script.getCode(), script.getName());
            chunk.call();
        }
    }

    private void loadSaturdayLibs() {
        for (LuaLib luaLib : libs) {

            luaLib.setGlobals(globals);

            if (luaLib instanceof LuaModule) {
                LuaModule luaModule = (LuaModule) luaLib;

                globals.load(new TwoArgFunction() {
                    @Override
                    public LuaValue call(LuaValue modname, LuaValue env) {
                        LuaTable module = luaModule.getInstance();
                        env.get("package").get("loaded").set(luaModule.name(), module);
                        return module;
                    }
                });

            } else if (luaLib instanceof LuaFunction) {

                LuaFunction luaFunction = (LuaFunction) luaLib;
                globals.set(luaFunction.name(), luaFunction.getInstance());

            } else if (luaLib instanceof InternalServiceLuaTable) {
                InternalServiceLuaTable internalServiceLuaTable = (InternalServiceLuaTable) luaLib;

                globals.load(new TwoArgFunction() {
                    @Override
                    public LuaValue call(LuaValue modname, LuaValue env) {
                        LuaTable table = internalServiceLuaTable.getInstance();
                        env.set(internalServiceLuaTable.name(), table);
                        return table;
                    }
                });

            } else {
                throw new RuntimeException(String.format("Unsupported: \"%s\"", luaLib.getClass().getCanonicalName())); // +
            }
        }
    }

}
