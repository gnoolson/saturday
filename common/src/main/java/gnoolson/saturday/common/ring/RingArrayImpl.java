package gnoolson.saturday.common.ring;


public class RingArrayImpl<T> implements Ring<T> {

    private final Object[] data;
    private int head;
    private int tail;
    private int size;

    /*
     *
     *
     * */
    public RingArrayImpl(int capacity) {
        data = new Object[capacity];
    }

    public void add(T value) {
        data[tail] = value;
        tail = (tail + 1) % data.length;

        if (size == data.length) {
            head = (head + 1) % data.length;
        } else {
            size++;
        }
    }

    public T poll() {
        if (size == 0) {
            return null;
        }

        T result = (T) data[head];
        head = (head + 1) % data.length;
        size--;

        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

}
