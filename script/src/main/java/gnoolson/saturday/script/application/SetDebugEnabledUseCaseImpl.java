package gnoolson.saturday.script.application;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.ScriptId;
import gnoolson.saturday.script.port.inbound.SetDebugEnabledUseCase;
import gnoolson.saturday.script.port.outbound.SetDebugEnabledGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetDebugEnabledUseCaseImpl implements SetDebugEnabledUseCase {

    private final SetDebugEnabledGateway setDebugEnabledGateway;
    private final Locker locker;

    /*
     *
     *
     * */
    @Override
    public void execute(ScriptId id, boolean flag) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            setDebugEnabledGateway.execute(id, flag);
        }
    }

}
