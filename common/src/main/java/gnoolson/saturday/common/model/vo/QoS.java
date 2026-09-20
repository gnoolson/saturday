package gnoolson.saturday.common.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class QoS {

    private final int value;

    /*
     *
     *
     * */
    public QoS(int value) {
        if (value < 0 || value > 2)
            throw new IllegalArgumentException("Unsupported QOS value: " + value); // +
        this.value = value;
    }

    public static QoS of(int value) {
        return new QoS(value);
    }

}
