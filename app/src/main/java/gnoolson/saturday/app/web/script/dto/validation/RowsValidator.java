package gnoolson.saturday.app.web.script.dto.validation;

public class RowsValidator {

    public static void check(int number) {
        if (number < 1 || number > 10000)
            throw new RuntimeException("Number must be between 1 and 10000 inclusive"); // +
    }

}
