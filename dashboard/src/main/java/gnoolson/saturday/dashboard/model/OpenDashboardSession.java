package gnoolson.saturday.dashboard.model;

import gnoolson.saturday.common.model.vo.DashboardName;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.ring.Ring;
import gnoolson.saturday.common.ring.RingArrayImpl;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.time.vo.TimeInMs;
import gnoolson.saturday.dashboard.model.vo.DashboardResponse;
import gnoolson.saturday.dashboard.model.vo.OpenDashboardRequestId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class OpenDashboardSession {

    private final TimeInMs TTL = TimeInMs.of(5000);
    @Getter
    private final OpenDashboardId openDashboardId;
    @Getter
    private final DashboardName dashboardName;
    @Getter
    private final ProjectId projectId;
    private final Ring<UndeliveredResponse> undeliveredResponses = new RingArrayImpl<>(16);
    private final TimeProvider timeProvider;

    private OpenDashboardRequest request;

    /*
     *
     *
     * */
    public synchronized void registrationRequest(OpenDashboardRequest openDashboardRequest) {
        if (undeliveredResponses.isEmpty()) {
            this.request = openDashboardRequest;
            return;
        }

        List<Object> listToResponse = new ArrayList<>(undeliveredResponses.size());
        for (int i = 0; i < undeliveredResponses.size(); i++) {
            UndeliveredResponse undeliveredResponse = undeliveredResponses.poll();
            if (undeliveredResponse.expired.isMore(timeProvider.now()))
                listToResponse.add(undeliveredResponse.response);
        }

        if (listToResponse.isEmpty()) {
            this.request = openDashboardRequest;
        } else {
            openDashboardRequest.setResult(listToResponse);
        }
    }

    public synchronized void send(DashboardResponse response) {
        if (request != null) {
            request.setResult(Collections.singletonList(response.getValue()));
            request = null;
        } else {
            undeliveredResponses.add(new UndeliveredResponse(response, timeProvider.now().add(TTL)));
        }
    }

    public synchronized void clearRequest() {
        request = null;
    }

    public synchronized Optional<OpenDashboardRequestId> getRequestId() {
        if (request == null)
            return Optional.empty();

        return Optional.of(request.getId());
    }

    /*
     *
     *
     * */
    @RequiredArgsConstructor
    private static class UndeliveredResponse {
        final DashboardResponse response;
        final TimeInMs expired;
    }

}
