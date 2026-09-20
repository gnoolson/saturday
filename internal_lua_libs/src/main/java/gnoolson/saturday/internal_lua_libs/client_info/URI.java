package gnoolson.saturday.internal_lua_libs.client_info;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class URI {

    private final String value;

    /*
     *
     *
     * */
    public URI(String value) {
        ValueObjectValidator.checkNotNull(value, "URI");
        this.value = value.trim();
        if (this.value.isEmpty())
            throw new RuntimeException("URL cannot be empty"); // +
    }

    public static URI of(String value) {
        return new URI(value);
    }

}
