package gnoolson.saturday.common.cache;

import gnoolson.saturday.common.model.vo.Id;

public interface EntityProvider<I extends Id, V> {

    Entity<V> create(I id);

}
