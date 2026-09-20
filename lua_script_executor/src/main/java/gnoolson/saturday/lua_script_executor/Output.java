package gnoolson.saturday.lua_script_executor;


public class Output {

    private final StringBuilder buffer = new StringBuilder();

    /*
     *
     *
     * */
    public String toString() {
        return buffer.toString();
    }

    public void println(String value) {
        if (value == null)
            value = "null";

        buffer.append(value);
        buffer.append('\n');
    }

    public void println() {
        buffer.append('\n');
    }

}
