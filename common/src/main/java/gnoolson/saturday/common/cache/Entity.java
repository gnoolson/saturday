package gnoolson.saturday.common.cache;

public interface Entity<ENTITY_VALUE> {

    CachingTime getCachingTime();

    ENTITY_VALUE getValue();

    void destroy();

}
