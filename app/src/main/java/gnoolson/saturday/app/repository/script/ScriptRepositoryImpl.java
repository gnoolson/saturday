package gnoolson.saturday.app.repository.script;

import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptMapper;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.common.model.vo.ScriptName;
import gnoolson.saturday.script.model.entity.Script;
import gnoolson.saturday.script.port.outbound.ScriptRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ScriptRepositoryImpl implements ScriptRepositoryGateway {

    private final ScriptJPARepository scriptJPARepository;

    /*
     *
     *
     * */
    @Cacheable(value = "script_find_by_id", key = "#id")
    @Override
    public Optional<Script> find(ScriptId id) {
        Optional<ScriptEntity> scriptEntityOpt = scriptJPARepository.findById(id.getValue());
        return scriptEntityOpt.map(ScriptMapper::toDomain);
    }


    @Override
    public List<Script> findAll() {
        Iterable<ScriptEntity> all = scriptJPARepository.findAll();
        return ScriptMapper.toDomain(all);
    }


    @Override
    public Optional<Script> find(ProjectId projectId, ScriptName name) {
        Optional<ScriptEntity> scriptEntityOpt = scriptJPARepository.find(projectId.getValue(), name.getValue());
        return scriptEntityOpt.map(ScriptMapper::toDomain);
    }


    @Override
    public Optional<Script> find(ProjectId projectId, ScriptId id) {
        Optional<ScriptEntity> scriptEntityOpt = scriptJPARepository.find(projectId.getValue(), id.getValue());
        return scriptEntityOpt.map(ScriptMapper::toDomain);
    }


    @Override
    public List<ScriptId> findOwners(ScriptId scriptId) {
        return scriptJPARepository.findOwners(scriptId.getValue().toString()).stream().map(ScriptId::of).collect(Collectors.toList());
    }


    @Override
    public List<Script> findAllAutostartScripts() {
        Iterable<ScriptEntity> all = scriptJPARepository.findAllAutostartScripts();
        return ScriptMapper.toDomain(all);
    }


    @CacheEvict(value = "script_find_by_id", key = "#script.id")
    @Override
    public ScriptId save(Script script) {
        ScriptEntity scriptEntity = ScriptMapper.toJPA(script);
        scriptEntity = scriptJPARepository.save(scriptEntity);
        return ScriptId.of(scriptEntity.getId());
    }


    @Caching(evict = {
            @CacheEvict(value = "script_find_by_id", key = "#id"),
            @CacheEvict(value = "dashboard_find_by_id", allEntries = true)
    })
    @Override
    public void delete(ScriptId id) {
        scriptJPARepository.delete(new ScriptEntity(id.getValue()));
    }

}
