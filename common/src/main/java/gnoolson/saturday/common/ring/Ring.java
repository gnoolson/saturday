package gnoolson.saturday.common.ring;


public interface Ring<T> {

    void add(T object);

    T poll();

    boolean isEmpty();

    int size();

}
