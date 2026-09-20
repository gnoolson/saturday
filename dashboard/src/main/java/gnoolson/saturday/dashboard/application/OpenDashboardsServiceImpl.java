package gnoolson.saturday.dashboard.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.cache.Cache;
import gnoolson.saturday.common.cache.CachingTime;
import gnoolson.saturday.common.cache.Entity;
import gnoolson.saturday.common.cache.EntityProvider;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.DashboardId;
import gnoolson.saturday.common.model.vo.OpenDashboardId;
import gnoolson.saturday.common.model.vo.ProjectId;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.time.vo.TimeInSec;
import gnoolson.saturday.dashboard.model.OpenDashboardRequest;
import gnoolson.saturday.dashboard.model.OpenDashboardSession;
import gnoolson.saturday.dashboard.model.entity.Dashboard;
import gnoolson.saturday.dashboard.model.exception.DashboardNotFoundException;
import gnoolson.saturday.dashboard.model.vo.DashboardResponse;
import gnoolson.saturday.dashboard.model.vo.OpenDashboardRequestId;
import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Log4j2
@RequiredArgsConstructor
public class OpenDashboardsServiceImpl implements OpenDashboardsService {

    private final Cache<OpenDashboardId, OpenDashboardSession> cache;
    private final Locker locker;
    private final TimeProvider timeProvider;
    private final DashboardRepositoryGateway dashboardRepositoryGateway;

    /*
     *
     *
     * */
    public OpenDashboardsServiceImpl(TimeProvider timeProvider, Locker locker, DashboardRepositoryGateway dashboardRepositoryGateway) {
        this.locker = locker;
        this.timeProvider = timeProvider;
        this.dashboardRepositoryGateway = dashboardRepositoryGateway;
        this.cache = new Cache<>(TimeInSec.of(5), timeProvider, new EntityProviderImpl(), locker);
    }

    @Override
    public boolean send(OpenDashboardId openDashboardId, DashboardResponse response) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(openDashboardId))) {
            Optional<OpenDashboardSession> sessionOpt = cache.getIfExists(openDashboardId);
            if (!sessionOpt.isPresent())
                return false;

            OpenDashboardSession openDashboardSession = sessionOpt.get();
            openDashboardSession.send(response);
            return true;
        }
    }

    @Override
    public List<OpenDashboardSession> getOpenDashboardSessionsInProject(ProjectId id) {
        return sessions().stream().filter(session -> {
            return session.getProjectId().equals(id);
        }).collect(Collectors.toList());
    }

    @Override
    public void registration(OpenDashboardId openDashboardId, OpenDashboardRequest openDashboardRequest) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(openDashboardId))) {
            OpenDashboardSession openDashboardSession = cache.getAndProlongOrCreate(openDashboardId);
            openDashboardSession.registrationRequest(openDashboardRequest);
        }
    }

    @Override
    public void timeout(OpenDashboardRequestId id) {
        for (OpenDashboardSession openDashboardSession : sessions()) {
            Optional<OpenDashboardRequestId> openDashboardRequestIdOpt = openDashboardSession.getRequestId();
            openDashboardRequestIdOpt.ifPresent(openDashboardRequestId -> {
                if (openDashboardRequestId.equals(id)) {
                    openDashboardSession.clearRequest();
                }
            });
        }
    }

    @Override
    public List<OpenDashboardSession> getOpenDashboardSessions(DashboardId id) {
        return sessions().stream().filter(session -> {
            return session.getOpenDashboardId().getDashboardId().equals(id);
        }).collect(Collectors.toList());
    }

    /*
     *
     *
     * */
    private List<OpenDashboardSession> sessions() {
        Set<OpenDashboardId> ids = cache.getIds();
        List<OpenDashboardSession> result = new ArrayList<>();

        for (OpenDashboardId openDashboardId : ids) {
            Optional<OpenDashboardSession> sessionOpt = cache.getIfExists(openDashboardId);
            sessionOpt.ifPresent(result::add);
        }

        return result;
    }

    private class EntityProviderImpl implements EntityProvider<OpenDashboardId, OpenDashboardSession> {
        @Override
        public Entity<OpenDashboardSession> create(OpenDashboardId id) {
            Dashboard dashboard = dashboardRepositoryGateway.find(id.getDashboardId()).orElseThrow(() -> new DashboardNotFoundException(id.getDashboardId()));

            OpenDashboardSession openDashboardSession = new OpenDashboardSession(id, dashboard.getName(), dashboard.getProjectId(), timeProvider);

            return new Entity<OpenDashboardSession>() {
                @Override
                public CachingTime getCachingTime() {
                    return CachingTime.of(45);
                }

                @Override
                public OpenDashboardSession getValue() {
                    return openDashboardSession;
                }

                @Override
                public void destroy() {
                }
            };
        }
    }

}
