package gnoolson.saturday.client.model.vo;

import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.common.validator.DomainModelValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.exception.ExceptionUtils;

@EqualsAndHashCode
@ToString
@Getter
public class ConnectionError {

    private final String message;
    private final TimeInMs timestamp;

    /*
     *
     *
     * */
    private ConnectionError(String message, TimeInMs timestamp) {
        DomainModelValidator.checkNotNull(message, "Message"); // +
        DomainModelValidator.checkNotNull(timestamp, "TimeInMs"); // +

        this.message = message;
        this.timestamp = timestamp;
    }

    public static ConnectionError empty() {
        return new ConnectionError("", TimeInMs.zero());
    }

    public static ConnectionError of(Exception exception, TimeInMs timestamp) {
        String message = ExceptionUtils.getRootCauseMessage(exception);
        message += ". ";
        message += exception.getLocalizedMessage();
        return new ConnectionError(message, timestamp);
    }

    public static ConnectionError of(String message, TimeInMs timestamp) {
        return new ConnectionError(message, timestamp);
    }

    public boolean exists() {
        return !message.isEmpty();
    }

}
