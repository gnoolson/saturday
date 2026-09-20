package gnoolson.saturday.app.web.dto;

public interface Regexes {

    String CLIENT_NAME = "^[A-Za-z0-9#_]{1,64}$";
    String SERVER_URI = "^(tcp|ssl|ws|wss)://([a-zA-Z0-9.-]+|\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d{1,5})$";
    String TOPIC_FILTER_FOR_SUBSCRIPTION = "^(([^#+\\u0000/]+|\\+)(/([^#+\\u0000/]+|\\+))*)?(/#)?$";
    String USERNAME = "^[A-Za-z0-9#_]{1,64}$";

}
