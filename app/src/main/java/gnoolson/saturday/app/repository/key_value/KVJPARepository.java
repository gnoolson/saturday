package gnoolson.saturday.app.repository.key_value;

import gnoolson.saturday.app.repository.key_value.entity.KVEntity;
import org.springframework.data.repository.CrudRepository;

public interface KVJPARepository extends CrudRepository<KVEntity, String> {

}
