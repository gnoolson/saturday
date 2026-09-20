package gnoolson.saturday.common.time.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class TimeInMs {

    private final long value;

    /*
     *
     *
     * */
    private TimeInMs(long value) {
        if (value < 0) throw new IllegalArgumentException("TimeInMs cannot be negative"); //+

        this.value = value;
    }

    public static TimeInMs of(long value) {
        return new TimeInMs(value);
    }

    public static TimeInMs zero() {
        return new TimeInMs(0);
    }

    public TimeInMs add(TimeInMs time) {
        return TimeInMs.of(this.value + time.getValue());
    }

    public boolean isMore(TimeInMs time) {
        return value > time.getValue();
    }

}
