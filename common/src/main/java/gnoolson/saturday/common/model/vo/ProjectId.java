package gnoolson.saturday.common.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class ProjectId implements Id {

    private final UUID value;

    /*
     *
     *
     * */
    private ProjectId() {
        this.value = EmptyId.getValue();
    }

    public ProjectId(UUID value) {
        ValueObjectValidator.checkNotNull(value, "projectId"); // +
        this.value = value;
    }

    public static ProjectId of(UUID value) {
        return new ProjectId(value);
    }

    public static ProjectId empty() {
        return new ProjectId();
    }

    public static ProjectId random() {
        return new ProjectId(UUID.randomUUID());
    }

    public boolean isEmpty() {
        return EmptyId.isEmpty(this.value);
    }

    @Override
    public String getStringValue() {
        return "project_id:" + value.toString();
    }

}
