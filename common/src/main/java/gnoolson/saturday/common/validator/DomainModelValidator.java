package gnoolson.saturday.common.validator;

public class DomainModelValidator {

    public static void checkNotNull(Object object, String name) {
        if (object == null)
            throw new IllegalArgumentException(String.format("\"%s\" cannot be null", name)); // +
    }

}
