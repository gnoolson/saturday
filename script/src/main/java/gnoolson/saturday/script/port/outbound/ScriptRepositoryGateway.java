package gnoolson.saturday.script.port.outbound;

import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.entity.Script;

import java.util.List;
import java.util.Optional;

public interface ScriptRepositoryGateway {

    Optional<Script> find(ScriptId scriptId);

    ScriptId save(Script script);

    List<Script> findAll();

    Optional<Script> find(ProjectId projectId, ScriptName name);

    Optional<Script> find(ProjectId projectId, ScriptId id);

    void delete(ScriptId scriptId);

    List<ScriptId> findOwners(ScriptId scriptId);

    List<Script> findAllAutostartScripts();

}
