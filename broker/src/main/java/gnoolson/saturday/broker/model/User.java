package gnoolson.saturday.broker.model;

import gnoolson.saturday.common.model.vo.AuthPassword;
import gnoolson.saturday.common.model.vo.AuthUsername;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {

    private final AuthUsername username;
    private final AuthPassword password;

}
