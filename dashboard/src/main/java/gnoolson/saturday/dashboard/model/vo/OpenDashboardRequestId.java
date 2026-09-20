package gnoolson.saturday.dashboard.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class OpenDashboardRequestId {

    private final UUID value;

    /*
     *
     *
     * */
    public OpenDashboardRequestId() {
        this.value = UUID.randomUUID();
    }

}
