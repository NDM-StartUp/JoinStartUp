package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Exceptions.UserWithThisEmailAlreadyExists;
import ndmstartup.joinstartup.Models.AppUserDetails;
import ndmstartup.joinstartup.Repositories.AppUserDetailsRepository;
import ndmstartup.joinstartup.Services.Interfaces.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUserDetails register(AppUserDetails user) {
        userDetailsRepository.findByEmail(user.getEmail())
                .ifPresent(appUserDetails -> {
                    throw new UserWithThisEmailAlreadyExists("Email: " + user.getEmail() + " is already registered");
                });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDetailsRepository.save(user);
    }

}
