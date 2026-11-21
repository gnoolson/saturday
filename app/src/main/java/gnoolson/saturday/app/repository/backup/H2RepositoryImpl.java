package gnoolson.saturday.app.repository.backup;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Log4j2
@Repository
public class H2RepositoryImpl implements H2Repository {

    @PersistenceContext
    private EntityManager entityManager;

    /*
     *
     *
     * */
    @Override
    public void createBackup(String path) {
        String sql = String.format("BACKUP TO '%s';", path);
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

}
