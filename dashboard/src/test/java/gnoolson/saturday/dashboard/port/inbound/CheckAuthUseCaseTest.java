//package gnoolson.saturday.dashboard.port.inbound;
//
//import gnoolson.saturday.common.model.vo.DashboardId;
//import gnoolson.saturday.common.model.vo.DashboardName;
//import gnoolson.saturday.dashboard.application.CheckAuthUseCaseImpl;
//import gnoolson.saturday.dashboard.model.entity.Dashboard;
//import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CheckAuthUseCaseTest {
//
//    @Test
//    public void check_opened_dashboard(){
//
//        DashboardRepositoryGateway dashboardRepositoryGateway = new DashboardRepositoryGateway() {
//            @Override
//            public Optional<Dashboard> find(DashboardId id) {
//                Assertions.assertEquals(DashboardId.of(1000), id);
//
//                Dashboard dashboard = Mockito.mock(Dashboard.class);
//                Mockito.when(dashboard.isOpenAccess()).thenReturn(true);
//
//                return Optional.of(dashboard);
//            }
//
//            @Override
//            public List<Dashboard> findAll() {
//                return Collections.emptyList();
//            }
//
//            @Override
//            public DashboardId save(Dashboard dashboard) {
//                return null;
//            }
//
//            @Override
//            public Optional<Dashboard> find(DashboardName name) {
//                return Optional.empty();
//            }
//
//            @Override
//            public void delete(DashboardId id) {
//
//            }
//
//            @Override
//            public List<Dashboard> findAllWithOpenAccess() {
//                return Collections.emptyList();
//            }
//        };
//
//        CheckAuthUseCase checkAuthUseCase = new CheckAuthUseCaseImpl(dashboardRepositoryGateway);
//
//        boolean result = checkAuthUseCase.execute(DashboardId.of(1000), false);
//
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    public void check_closed_dashboard__success(){
//
//        DashboardRepositoryGateway dashboardRepositoryGateway = new DashboardRepositoryGateway() {
//            @Override
//            public Optional<Dashboard> find(DashboardId id) {
//                Assertions.assertEquals(DashboardId.of(1000), id);
//
//                Dashboard dashboard = Mockito.mock(Dashboard.class);
//                Mockito.when(dashboard.isOpenAccess()).thenReturn(false);
//
//                return Optional.of(dashboard);
//            }
//
//            @Override
//            public List<Dashboard> findAll() {
//                return Collections.emptyList();
//            }
//
//            @Override
//            public DashboardId save(Dashboard dashboard) {
//                return null;
//            }
//
//            @Override
//            public Optional<Dashboard> find(DashboardName name) {
//                return Optional.empty();
//            }
//
//            @Override
//            public void delete(DashboardId id) {
//
//            }
//
//            @Override
//            public List<Dashboard> findAllWithOpenAccess() {
//                return Collections.emptyList();
//            }
//        };
//
//        CheckAuthUseCase checkAuthUseCase = new CheckAuthUseCaseImpl(dashboardRepositoryGateway);
//
//        boolean result = checkAuthUseCase.execute(DashboardId.of(1000), true);
//
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    public void check_closed_dashboard__fail(){
//
//        DashboardRepositoryGateway dashboardRepositoryGateway = new DashboardRepositoryGateway() {
//            @Override
//            public Optional<Dashboard> find(DashboardId id) {
//                Assertions.assertEquals(DashboardId.of(1000), id);
//
//                Dashboard dashboard = Mockito.mock(Dashboard.class);
//                Mockito.when(dashboard.isOpenAccess()).thenReturn(false);
//
//                return Optional.of(dashboard);
//            }
//
//            @Override
//            public List<Dashboard> findAll() {
//                return Collections.emptyList();
//            }
//
//            @Override
//            public DashboardId save(Dashboard dashboard) {
//                return null;
//            }
//
//            @Override
//            public Optional<Dashboard> find(DashboardName name) {
//                return Optional.empty();
//            }
//
//            @Override
//            public void delete(DashboardId id) {
//
//            }
//
//            @Override
//            public List<Dashboard> findAllWithOpenAccess() {
//                return Collections.emptyList();
//            }
//        };
//
//        CheckAuthUseCase checkAuthUseCase = new CheckAuthUseCaseImpl(dashboardRepositoryGateway);
//
//        boolean result = checkAuthUseCase.execute(DashboardId.of(1000), false);
//
//        Assertions.assertFalse(result);
//    }
//
//}