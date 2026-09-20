package gnoolson.saturday.app.web;

import gnoolson.saturday.common.model.vo.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtil {

    public static Role getRole() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            List<? extends GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

            if (authorities.size() > 1)
                throw new RuntimeException("User cannot have more than one role"); // +

            String authority = authorities.get(0).getAuthority();

            if (authority.equals("ROLE_EDITOR"))
                return Role.EDITOR;
            if (authority.equals("ROLE_VIEWER"))
                return Role.VIEWER;
            if (authority.equals("ROLE_ANONYMOUS"))
                return Role.ANONYMOUS;

            throw new IllegalStateException("Unsupported: " + authority); // +
        } else {
            return Role.ANONYMOUS;
        }
    }

}
