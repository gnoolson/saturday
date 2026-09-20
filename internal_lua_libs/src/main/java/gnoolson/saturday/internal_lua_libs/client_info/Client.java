package gnoolson.saturday.internal_lua_libs.client_info;

import gnoolson.saturday.common.model.vo.ClientId;
import gnoolson.saturday.common.model.vo.ClientName;
import gnoolson.saturday.common.model.vo.ProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Client {

    private final ClientId id;
    private final ClientName name;
    private final ProjectId projectId;
    private final boolean connected;
    private final URI URI;

}
