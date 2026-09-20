package gnoolson.saturday.internal_lua_libs.open_dashboard;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.Getter;

@Getter
public class OutgoingMessage {

    private final Object data;

    /*
     *
     *
     * */
    public OutgoingMessage(Object data) {
        ValueObjectValidator.checkNotNull(data, "OutgoingMessage");
        this.data = data;
    }

}