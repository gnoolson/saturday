package gnoolson.saturday.common.locker;

import gnoolson.saturday.common.model.vo.*;

public class LockId {

    public static String of(ClientName name) {
        String key = "client_name:" + name.getValue();
        return key;
    }

    public static String of(ScriptName name) {
        String key = "script_name:" + name.getValue();
        return key;
    }

    public static String of(ScheduleName name) {
        String key = "schedule_name:" + name.getValue();
        return key;
    }

    public static String of(DashboardName name) {
        String key = "dashboard_name:" + name.getValue();
        return key;
    }

    public static String of(ProjectName name) {
        String key = "project_name:" + name.getValue();
        return key;
    }

    public static String of(Username username) {
        String key = "username:" + username.getValue();
        return key;
    }

    public static String of(Id id) {
        return id.getStringValue();
    }


}
