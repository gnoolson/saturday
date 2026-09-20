package gnoolson.saturday.common.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class PositiveNumber {

    private final int value;

    /*
     *
     *
     * */
    public PositiveNumber(int value) {
        if (value < 0)
            throw new IllegalArgumentException("PositiveNumber cannot be negative"); // +
        this.value = value;
    }

    public static PositiveNumber of() {
        return new PositiveNumber(0);
    }

    public static PositiveNumber of(int value) {
        return new PositiveNumber(value);
    }

    public static PositiveNumber of(int value, int max) {
        return new PositiveNumber(value);
    }

    public PositiveNumber add(PositiveNumber positiveNumber) {
        return new PositiveNumber(value + positiveNumber.value);
    }

    public boolean isNotZero() {
        return value != 0;
    }

    public boolean isZero() {
        return value == 0;
    }

    public boolean isLessOrEqual(PositiveNumber positiveNumber) {
        return value <= positiveNumber.value;
    }

    public boolean isLess(PositiveNumber positiveNumber) {
        return value < positiveNumber.value;
    }

}
