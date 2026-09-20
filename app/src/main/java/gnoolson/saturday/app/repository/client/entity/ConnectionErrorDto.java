package gnoolson.saturday.app.repository.client.entity;

import gnoolson.saturday.app.utils.JSON;
import gnoolson.saturday.client.model.vo.ConnectionError;
import lombok.Data;

@Data
public class ConnectionErrorDto {

    private String message;
    private long timestamp;

    public ConnectionErrorDto(ConnectionError connectionError) {
        this.message = connectionError.getMessage();
        this.timestamp = connectionError.getTimestamp().getValue();
    }

    public ConnectionErrorDto() {
    }

    public static ConnectionErrorDto fromJSON(String json) {
        return JSON.parseObject(json, ConnectionErrorDto.class);
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }


}
