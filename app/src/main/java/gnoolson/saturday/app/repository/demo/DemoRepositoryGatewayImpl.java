package gnoolson.saturday.app.repository.demo;

import gnoolson.saturday.app.repository.key_value.KVJPARepository;
import gnoolson.saturday.app.repository.key_value.entity.KVEntity;
import gnoolson.saturday.export_import.port.outbound.DemoRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DemoRepositoryGatewayImpl implements DemoRepositoryGateway {

    private final String KEY = "DEMO_INSTALLED";
    private final KVJPARepository kvjpaRepository;

    /*
     *
     *
     * */
    @Override
    public boolean get() {
        Optional<KVEntity> entityOpt = kvjpaRepository.findById(KEY);
        if (!entityOpt.isPresent())
            return false;

        KVEntity kvEntity = entityOpt.get();
        return Boolean.parseBoolean(kvEntity.getValue());
    }

    @Override
    public void save(boolean flag) {
        KVEntity entity = new KVEntity(KEY, String.valueOf(true));
        kvjpaRepository.save(entity);
    }

}
