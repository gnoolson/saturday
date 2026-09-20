package gnoolson.saturday.app.config;

import gnoolson.saturday.common.model.vo.Username;
import gnoolson.saturday.user.model.entity.User;
import gnoolson.saturday.user.port.outbound.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryGateway userRepositoryGateway;

    /*
     *
     *
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepositoryGateway.findByUsername(Username.of(username));

        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_" + user.getRole().name();
                    }
                });
            }

            @Override
            public String getPassword() {
                return user.getPassword().getValue();
            }

            @Override
            public String getUsername() {
                return user.getUsername().getValue();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

}
