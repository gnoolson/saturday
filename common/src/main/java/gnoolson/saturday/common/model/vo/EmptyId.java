package gnoolson.saturday.common.model.vo;

import java.util.UUID;

public class EmptyId {

    private final static UUID VALUE = new UUID(0, 0);

    /*
     *
     *
     * */
    public static UUID getValue() {
        return VALUE;
    }

    public static boolean isEmpty(UUID id) {
        return VALUE.equals(id);
    }

}
