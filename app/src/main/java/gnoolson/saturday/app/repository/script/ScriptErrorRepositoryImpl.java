package gnoolson.saturday.app.repository.script;

import gnoolson.saturday.app.repository.script.entity.ScriptErrorEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptErrorMapper;
import gnoolson.saturday.common.model.vo.PositiveNumber;
import gnoolson.saturday.common.model.vo.ScriptErrorId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.model.entity.ScriptError;
import gnoolson.saturday.script.port.outbound.ScriptErrorRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ScriptErrorRepositoryImpl implements ScriptErrorRepositoryGateway {

    private final ScriptErrorJPARepository scriptErrorJPARepository;

    /*
     *
     *
     * */
    @Override
    public List<ScriptError> find(ScriptId scriptId) {
        List<ScriptErrorEntity> entities = scriptErrorJPARepository.find(scriptId.getValue());
        return ScriptErrorMapper.toDomain(entities);
    }


    @Override
    public PositiveNumber count(ScriptId scriptId) {
        long count = scriptErrorJPARepository.count(scriptId.getValue());
        return PositiveNumber.of((int) count);
    }


    @Override
    public ScriptErrorId save(ScriptError scriptError) {
        ScriptErrorEntity scriptErrorEntity = ScriptErrorMapper.toJPA(scriptError);
        ScriptErrorEntity save = scriptErrorJPARepository.save(scriptErrorEntity);
        return ScriptErrorId.of(save.getId());
    }


    @Override
    public void deleteAll(ScriptId scriptId) {
        scriptErrorJPARepository.deleteByScriptId(scriptId.getValue());
    }


}
