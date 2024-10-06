package ndmstartup.joinstartup.Security.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Security.Abstractions.AppUserPrincipal;
import ndmstartup.joinstartup.Models.AppUserDetails;
import ndmstartup.joinstartup.Repositories.AppUserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUserDetails userDetails = userDetailsRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return new AppUserPrincipal(userDetails);
    }
}
