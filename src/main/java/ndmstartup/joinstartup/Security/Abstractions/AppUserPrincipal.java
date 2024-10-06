package ndmstartup.joinstartup.Security.Abstractions;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Models.AppUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AppUserPrincipal implements UserDetails {
    private final AppUserDetails userDetails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(userDetails.getRoles().stream().map(SimpleGrantedAuthority::new).toArray(GrantedAuthority[]::new));
    }

    @Override
    public String getPassword() {
        return userDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetails.getEmail();
    }

    public Long getId(){
        return userDetails.getId();
    }
}
