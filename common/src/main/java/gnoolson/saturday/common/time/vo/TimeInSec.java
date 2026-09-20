package gnoolson.saturday.common.time.vo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class TimeInSec {

    private final int value;

    /*
     *
     *
     * */
    private TimeInSec(int value) {
        if (value < 0)
            throw new IllegalArgumentException("TimeInSec cannot be negative"); // +

        this.value = value;
    }

    public static TimeInSec of(int value) {
        return new TimeInSec(value);
    }

    public static TimeInSec zero() {
        return new TimeInSec(0);
    }

    public int getValue() {
        return value;
    }

    public TimeInSec add(TimeInSec time) {
        return TimeInSec.of(this.value + time.getValue());
    }

    public boolean isMore(TimeInSec time) {
        return value > time.getValue();
    }
}
