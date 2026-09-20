package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class ProjectName {

    private final String value;

    /*
     *
     *
     *
     * */
    public ProjectName(String value) {
        ValueObjectValidator.checkNotNull(value, "ProjectName"); // +
        this.value = value;
    }

    public static ProjectName of(String value) {
        return new ProjectName(value);
    }

    public static ProjectName empty() {
        return new ProjectName("");
    }

}
