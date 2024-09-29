package ndmstartup.joinstartup.Security.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Exceptions.UserWithThisEmailAlreadyExists;
import ndmstartup.joinstartup.Models.AppUserDetails;
import ndmstartup.joinstartup.Repositories.AppUserDetailsRepository;
import ndmstartup.joinstartup.Security.Services.Interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public void register(AppUserDetails user) {
        userDetailsRepository.findByEmail(user.getEmail())
                .ifPresent(appUserDetails -> {
                    throw new UserWithThisEmailAlreadyExists("Email: " + user.getEmail() + " is already registered");
                });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsRepository.save(user);
    }

    @Override
    public String verify(AppUserDetails user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(user);
        return "Logging failed. Wrong email or password!";
    }

}
