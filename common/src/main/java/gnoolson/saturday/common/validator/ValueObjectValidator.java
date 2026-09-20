package gnoolson.saturday.common.validator;

public class ValueObjectValidator {

    public static void checkNotNull(Object object, String name) {
        if (object == null)
            throw new IllegalArgumentException(String.format("\"%s\" value cannot be null", name)); // +
    }

}
