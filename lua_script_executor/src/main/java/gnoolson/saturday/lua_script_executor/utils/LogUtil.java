package gnoolson.saturday.lua_script_executor.utils;

public class LogUtil {

    public static String stringArrayToSingleString(String... strings) {
        if (strings.length == 0) {
            return "";
        } else if (strings.length == 1) {
            return strings[0];
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            String msg = strings[i];
            stringBuilder.append(msg);
            if (i < strings.length - 1)
                stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

}
