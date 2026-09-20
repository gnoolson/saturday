package gnoolson.saturday.app.repository.script;

import gnoolson.saturday.app.repository.script.entity.ScriptErrorEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ScriptErrorJPARepository extends CrudRepository<ScriptErrorEntity, Long> {

    @Query("select count(se) from ScriptError se where se.script.id = :scriptId")
    long count(@Param("scriptId") UUID scriptId);

    @Query("from ScriptError se where se.script.id = :scriptId ORDER BY id DESC")
    List<ScriptErrorEntity> find(@Param("scriptId") UUID scriptId);

    @Modifying
    @Query("delete from ScriptError se where se.script.id = :scriptId")
    void deleteByScriptId(@Param("scriptId") UUID scriptId);

}
