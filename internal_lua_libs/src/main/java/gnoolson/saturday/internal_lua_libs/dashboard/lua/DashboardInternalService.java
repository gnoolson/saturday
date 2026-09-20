package gnoolson.saturday.internal_lua_libs.dashboard.lua;

import gnoolson.luaj_utils.LuaArgUtils;
import gnoolson.luaj_utils.LuaTypeConverter;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.internal_lua_libs.dashboard.Dashboard;
import gnoolson.saturday.internal_lua_libs.dashboard.Id;
import gnoolson.saturday.internal_lua_libs.dashboard.IncomingMessage;
import gnoolson.saturday.internal_lua_libs.dashboard.OutgoingMessage;
import gnoolson.saturday.lua_script_executor.lib.InternalServiceLuaTable;
import lombok.RequiredArgsConstructor;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.Optional;

public class DashboardInternalService extends LuaTable implements InternalServiceLuaTable {

    private Dashboard dashboard;

    /*
     *
     *
     * */
    public DashboardInternalService() {
        set("isIncomingMessageAvailable", new IsIncomingMessageAvailableFunction(this));
        set("getIncomingMessage", new GetIncomingMessageFunction(this));
        set("sendMessage", new SendMessageFunction(this));
        set("getOpenDashboardId", new GetOpenDashboardIdFunction(this));
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {
        this.dashboard = (Dashboard) functionality;
    }

    @Override
    public void release() {

    }

    @Override
    public String name() {
        return "Dashboard";
    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    public static class IsIncomingMessageAvailableFunction extends ZeroArgFunction {

        private final DashboardInternalService dashboardInternalService;

        @Override
        public LuaValue call() {
            return LuaBoolean.valueOf(dashboardInternalService.dashboard.isMessageAvailable());
        }
    }

    @RequiredArgsConstructor
    public static class GetOpenDashboardIdFunction extends ZeroArgFunction {

        private final DashboardInternalService dashboardInternalService;

        @Override
        public LuaValue call() {
            Optional<OpenDashboardId> openDashboardIdOpt = dashboardInternalService.dashboard.getOpenDashboardId();
            if (!openDashboardIdOpt.isPresent())
                return LuaValue.NIL;

            return LuaString.valueOf(openDashboardIdOpt.get().getValue().toString());
        }
    }

    @RequiredArgsConstructor
    public static class GetIncomingMessageFunction extends ZeroArgFunction {

        private final DashboardInternalService dashboardInternalService;

        @Override
        public LuaValue call() {
            Optional<IncomingMessage> requestOpt = dashboardInternalService.dashboard.getMessage();
            if (!requestOpt.isPresent())
                return LuaValue.NIL;

            IncomingMessage incomingMessage = requestOpt.get();
            Object data = incomingMessage.getData();
            LuaValue result = LuaTypeConverter.toLua(data);

            return result;
        }
    }

    @RequiredArgsConstructor
    public static class SendMessageFunction extends OneArgFunction {

        private final DashboardInternalService dashboardInternalService;

        @Override
        public LuaValue call(LuaValue messageDataLuaTable) {
            Object messageData = LuaArgUtils.getObjectFromFunctionArgs(messageDataLuaTable, 1, "message");

            OutgoingMessage outgoingMessage = new OutgoingMessage(messageData);
            dashboardInternalService.dashboard.sendMessage(outgoingMessage);

            return LuaValue.NIL;
        }
    }

}
