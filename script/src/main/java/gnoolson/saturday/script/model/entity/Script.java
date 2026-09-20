package gnoolson.saturday.script.model.entity;

import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.model.vo.Description;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.common.validator.DomainModelValidator;
import gnoolson.saturday.script.model.vo.Code;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
public class Script {

    private final ScriptId id;
    private final ProjectId projectId;
    private ScriptName name;
    private Description description;
    private Code code;
    private boolean enabled;
    private boolean blocked;
    private List<ScriptId> includedScripts;
    private CachingTime cachingTime;
    private boolean autostart;
    private ScriptId errorEventHandler;

    /*
     *
     *
     * */
    public Script(ScriptId id, ProjectId projectId, ScriptName name, Description description, Code code, List<ScriptId> includedScripts, CachingTime cachingTime, boolean autostart, ScriptId errorEventHandler) {
        DomainModelValidator.checkNotNull(id, "ScriptId");
        DomainModelValidator.checkNotNull(projectId, "ProjectId");
        DomainModelValidator.checkNotNull(name, "ScriptName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(code, "Code");
        DomainModelValidator.checkNotNull(includedScripts, "IncludedScripts");
        DomainModelValidator.checkNotNull(cachingTime, "CachingTime");
        DomainModelValidator.checkNotNull(errorEventHandler, "ErrorEventHandler");

        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.code = code;
        this.includedScripts = includedScripts;
        this.cachingTime = cachingTime;
        this.autostart = autostart;
        this.errorEventHandler = errorEventHandler;
    }

    public Script(ScriptId id, ProjectId projectId, ScriptName name, Description description, Code code, boolean enabled, boolean blocked, List<ScriptId> includedScripts, CachingTime cachingTime, boolean autostart, ScriptId errorEventHandler) {
        DomainModelValidator.checkNotNull(id, "ScriptId");
        DomainModelValidator.checkNotNull(projectId, "ProjectId");
        DomainModelValidator.checkNotNull(name, "ScriptName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(code, "Code");
        DomainModelValidator.checkNotNull(includedScripts, "IncludedScripts");
        DomainModelValidator.checkNotNull(cachingTime, "CachingTime");
        DomainModelValidator.checkNotNull(errorEventHandler, "ErrorEventHandler");

        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.code = code;
        this.includedScripts = includedScripts;
        this.cachingTime = cachingTime;
        this.enabled = enabled;
        this.blocked = blocked;
        this.autostart = autostart;
        this.errorEventHandler = errorEventHandler;
    }

    public void disableAndBlock() {
        disable();
        this.blocked = true;
    }

    public void update(ScriptName name, Description description, CachingTime cachingTime, boolean autostart, ScriptId errorEventHandler) {
        DomainModelValidator.checkNotNull(name, "ScriptName");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(cachingTime, "CachingTime");
        DomainModelValidator.checkNotNull(errorEventHandler, "ErrorEventHandler");

//        if(this.blocked && enabled)
//            throw new RuntimeException("Script is blocked"); // +

        this.name = name;
        this.description = description;
        this.cachingTime = cachingTime;
        this.autostart = autostart;
        this.errorEventHandler = errorEventHandler;
    }

    public void update(Code code, List<ScriptId> includedScripts) {
        DomainModelValidator.checkNotNull(code, "Code");
        DomainModelValidator.checkNotNull(includedScripts, "IncludedScripts");

        this.code = code;
        this.includedScripts = includedScripts;
    }

    public void unblock() {
        this.blocked = false;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }


}
