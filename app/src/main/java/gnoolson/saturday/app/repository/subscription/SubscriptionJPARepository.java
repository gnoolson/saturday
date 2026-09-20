package gnoolson.saturday.app.repository.subscription;

import gnoolson.saturday.app.repository.subscription.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionJPARepository extends CrudRepository<SubscriptionEntity, UUID> {

    @Query("from Subscription s where s.client.id = :clientId")
    List<SubscriptionEntity> findByClientId(@Param("clientId") UUID clientId);

    @Query("from Subscription s where s.client.id = :clientId and topicFilter = :topicFilter")
    Optional<SubscriptionEntity> find(@Param("clientId") UUID clientId, @Param("topicFilter") String topicFilter);

}
