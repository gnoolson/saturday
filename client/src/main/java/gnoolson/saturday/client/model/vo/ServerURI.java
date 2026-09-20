package gnoolson.saturday.client.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ServerURI {

    private final String value;

    /*
     *
     *
     * */
    public ServerURI(String value) {
        ValueObjectValidator.checkNotNull(value, "ServerURI"); // +
        this.value = value;
    }

    public static ServerURI of(String value) {
        return new ServerURI(value);
    }

}
