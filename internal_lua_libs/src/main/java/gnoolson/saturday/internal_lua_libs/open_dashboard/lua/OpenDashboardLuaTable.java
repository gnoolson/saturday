package gnoolson.saturday.internal_lua_libs.open_dashboard.lua;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OpenDashboard;
import gnoolson.saturday.internal_lua_libs.open_dashboard.OutgoingMessage;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;


public class OpenDashboardLuaTable extends LuaTable {

    private final OpenDashboard openDashboard;

    /*
     *
     *
     * */
    public OpenDashboardLuaTable(OpenDashboard openDashboard) {
        this.openDashboard = openDashboard;

        set("id", openDashboard.getOpenDashboardId().getValue().toString());
        set("dashboardId", openDashboard.getOpenDashboardId().getDashboardId().getValue().toString());
        set("projectId", openDashboard.getProjectId().getValue().toString());
        set("name", openDashboard.getName().getValue());
        set("sendMessage", new SendMessageFunction());
    }

    /*
     *
     *
     * */
    public static class SendMessageFunction extends TwoArgFunction {
        @Override
        public LuaValue call(LuaValue self, LuaValue messageDataLuaValue) {
            OpenDashboardLuaTable openDashboardLuaTable = LuaArgUtils.getSelfFromFunctionArgs(self);
            Object data = LuaArgUtils.getObjectFromFunctionArgs(messageDataLuaValue, 2, "message");

            return openDashboardLuaTable.openDashboard.send(new OutgoingMessage(data)) ? LuaValue.TRUE : LuaValue.FALSE;
        }
    }

}
