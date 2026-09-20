//package gnoolson.saturday.dashboard.port.inbound;
//
//import gnoolson.locker.Locker;
//import gnoolson.locker.OptimisticLocalLocker;
//import gnoolson.saturday.common.model.vo.DashboardId;
//import gnoolson.saturday.common.model.vo.DashboardName;
//import gnoolson.saturday.common.model.vo.Description;
//import gnoolson.saturday.common.model.vo.ScriptId;
//import gnoolson.saturday.common.transaction.*;
//import gnoolson.saturday.dashboard.application.CreateDashboardUseCaseImpl;
//import gnoolson.saturday.dashboard.model.entity.Dashboard;
//import gnoolson.saturday.dashboard.model.vo.Html;
//import gnoolson.saturday.dashboard.port.outbound.DashboardRepositoryGateway;
//import gnoolson.saturday.dashboard.port.outbound.ScriptIdChecker;
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
//class CreateDashboardUseCaseTest {
//
//    final Locker locker = new OptimisticLocalLocker();
//
//    final TransactionStarter transactionStarter = new TransactionStarter(new TransactionSynchronizationManagerWrapper() {
//        @Override
//        public void register(TransactionSynchronizationAdapterWrapper wrapper) {
//        }
//    }, new TransactionExecutor() {
//        @Override
//        public void exec(TransactionBody transactionBody) {
//            transactionBody.exec();
//        }
//    });
//
//    final ScriptIdChecker scriptIdChecker = new ScriptIdChecker() {
//        @Override
//        public boolean exists(ScriptId id) {
//            return true;
//        }
//    };
//
//    @Test
//    public void create__success(){
//
//        CreateDashboardUseCase.DashboardDto dto = new CreateDashboardUseCase.DashboardDto(
//                DashboardName.generate(),
//                Html.of("<html></html>"),
//                Description.empty(),
//                ScriptId.of(100),
//                true
//        );
//
//        DashboardRepositoryGateway dashboardRepositoryGateway = new DashboardRepositoryGateway() {
//            @Override
//            public Optional<Dashboard> find(DashboardId id) {
//                return Optional.empty();
//            }
//
//            @Override
//            public List<Dashboard> findAll() {
//                return Collections.emptyList();
//            }
//
//            @Override
//            public DashboardId save(Dashboard dashboard) {
//                Assertions.assertEquals(dto.getName(), dashboard.getName());
//                Assertions.assertEquals(dto.getDescription(), dashboard.getDescription());
//                Assertions.assertEquals(dto.getHtml(), dashboard.getHtml());
//                Assertions.assertEquals(dto.getScriptId(), dashboard.getScriptId());
//                Assertions.assertEquals(dto.isOpenAccess(), dashboard.isOpenAccess());
//
//                return DashboardId.of(100_000);
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
//        CreateDashboardUseCase createDashboardUseCase = new CreateDashboardUseCaseImpl(dashboardRepositoryGateway, transactionStarter, locker, scriptIdChecker);
//
//        DashboardId dashboardId = createDashboardUseCase.execute(dto);
//        Assertions.assertEquals(DashboardId.of(100_000), dashboardId);
//    }
//
//    @Test
//    public void create__fail(){
//
//        CreateDashboardUseCase.DashboardDto dto = new CreateDashboardUseCase.DashboardDto(
//                DashboardName.generate(),
//                Html.of("<html></html>"),
//                Description.empty(),
//                ScriptId.of(100),
//                true
//        );
//
//        DashboardRepositoryGateway dashboardRepositoryGateway = new DashboardRepositoryGateway() {
//            @Override
//            public Optional<Dashboard> find(DashboardId id) {
//                return Optional.empty();
//            }
//
//            @Override
//            public List<Dashboard> findAll() {
//                return Collections.emptyList();
//            }
//
//            @Override
//            public DashboardId save(Dashboard dashboard) {
//                Assertions.assertTrue(false);
//
//                return DashboardId.empty();
//            }
//
//            @Override
//            public Optional<Dashboard> find(DashboardName name) {
//                if(name.equals(dto.getName()))
//                    return Optional.of(Mockito.mock(Dashboard.class));
//
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
//        CreateDashboardUseCase createDashboardUseCase = new CreateDashboardUseCaseImpl(dashboardRepositoryGateway, transactionStarter, locker, scriptIdChecker);
//
//
//        RuntimeException runtimeException = assertThrowsExactly(RuntimeException.class, () -> {
//            createDashboardUseCase.execute(dto);
//        });
//
//        Assertions.assertEquals("Name is already bound", runtimeException.getMessage());
//
//    }
//
//
//}