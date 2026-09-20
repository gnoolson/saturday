package gnoolson.saturday.common.cache;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class CachingTime {

    private final int value;

    /*
     *
     *
     * */
    public CachingTime(int sec) {
        if (sec < 0 || sec > 100_000)
            throw new RuntimeException("must be between 0 and 100000 inclusive"); // +

        this.value = sec;
    }

    public static CachingTime of(int value) {
        return new CachingTime(value);
    }

    public static CachingTime notCacheable() {
        return new CachingTime(0);
    }

    public boolean isCacheable() {
        return value > 0;
    }

}
