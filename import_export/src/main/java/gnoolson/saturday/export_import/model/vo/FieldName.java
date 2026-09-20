package gnoolson.saturday.export_import.model.vo;

public enum FieldName {

    PROJECT_ID("id"),
    PROJECT_NAME("name"),
    PROJECT_DESCRIPTION("description"),

    SCRIPT_ID("id"),
    SCRIPT_NAME("name"),
    SCRIPT_DESCRIPTION("description"),
    SCRIPT_CODE("code"),
    SCRIPT_INCLUDED_SCRIPTS("included_scripts"),
    SCRIPT_CACHING_TIME("caching_time"),
    SCRIPT_PROJECT_ID("project_id"),
    SCRIPT_AUTOSTART("autostart"),
    SCRIPT_ERROR_EVENT_HANDLER("error_event_handler"),

    CLIENT_ID("id"),
    CLIENT_NAME("name"),
    CLIENT_DESCRIPTION("description"),
    CLIENT_SERVER_URI("uri"),
    CLIENT_AUTH("auth"),
    CLIENT_CONNECTED_EVENT_HANDLER("connected_event_handler"),
    CLIENT_DISCONNECTED_EVENT_HANDLER("disconnected_event_handler"),
    CLIENT_PROJECT_ID("project_id"),

    SUBSCRIPTION_ID("id"),
    SUBSCRIPTION_CLIENT_ID("client_id"),
    SUBSCRIPTION_SCRIPT_ID("script_id"),
    SUBSCRIPTION_DESCRIPTION("description"),
    SUBSCRIPTION_TOPIC_FILTER("topic_filter"),
    SUBSCRIPTION_PROJECT_ID("project_id"),

    SCHEDULE_ID("id"),
    SCHEDULE_NAME("name"),
    SCHEDULE_CRON_EXPRESSION("cron"),
    SCHEDULE_DESCRIPTION("description"),
    SCHEDULE_SCRIPT_ID("script_id"),
    SCHEDULE_PROJECT_ID("project_id"),

    DASHBOARD_ID("id"),
    DASHBOARD_NAME("name"),
    DASHBOARD_DESCRIPTION("description"),
    DASHBOARD_HTML("html"),
    DASHBOARD_SCRIPT_ID("script_id"),
    DASHBOARD_ACCESS("access"),
    DASHBOARD_PROJECT_ID("project_id");

    private final String name;

    /*
     *
     *
     * */
    FieldName(String name) {
        this.name = name;
    }

    public static FieldName of(String name) {
        for (FieldName fieldName : FieldName.values()) {
            if (fieldName.getName().equals(name))
                return fieldName;
        }
        throw new IllegalArgumentException("Unsupported: " + name); // +
    }

    public String getName() {
        return name;
    }

}
