package gnoolson.saturday.internal_lua_libs.dashboard;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.Getter;

@Getter
public class IncomingMessage {

    private final Object data;

    /*
     *
     *
     * */
    public IncomingMessage(Object data) {
        ValueObjectValidator.checkNotNull(data, "IncomingMessage");
        this.data = data;
    }

}