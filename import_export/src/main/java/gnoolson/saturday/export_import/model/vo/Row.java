package gnoolson.saturday.export_import.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
public class Row {

    private final List<String> values;

    /*
     *
     *
     * */
    public Row(List<String> values) {
        if (values == null)
            throw new IllegalArgumentException("Row values cannot be null"); // +
        this.values = values;
    }

    public static Row of(List<String> values) {
        return new Row(values);
    }

}
