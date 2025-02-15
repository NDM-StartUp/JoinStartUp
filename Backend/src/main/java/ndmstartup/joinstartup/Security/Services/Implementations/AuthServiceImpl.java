package ndmstartup.joinstartup.Security.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.AuthDTO;
import ndmstartup.joinstartup.DTOs.GetUserAuthInfoDTO;
import ndmstartup.joinstartup.Exceptions.InvalidUsernameOrPasswordException;
import ndmstartup.joinstartup.Exceptions.UserWithThisEmailAlreadyExistsException;
import ndmstartup.joinstartup.Models.AppUserDetails;
import ndmstartup.joinstartup.Repositories.AppUserDetailsRepository;
import ndmstartup.joinstartup.Security.Abstractions.AppUserPrincipal;
import ndmstartup.joinstartup.Security.Services.Interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    public void register(AuthDTO user) {
        userDetailsRepository.findByEmail(user.getEmail())
                .ifPresent(appUserDetails -> {
                    throw new UserWithThisEmailAlreadyExistsException("Email: " + user.getEmail() + " is already registered");
                });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsRepository.save(AppUserDetails.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                .build());
    }

    @Override
    public String login(AuthDTO user) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            if(!authentication.isAuthenticated())
                throw new InvalidUsernameOrPasswordException("Invalid username or password");
            return jwtService.generateToken(AppUserDetails.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .id(((AppUserPrincipal) authentication.getPrincipal()).getId())
                    .build());
        }catch (BadCredentialsException e) {
            throw new InvalidUsernameOrPasswordException("Invalid username or password");
        }

    }

    @Override
    public GetUserAuthInfoDTO getUserAuthInfo(String token) {
        return GetUserAuthInfoDTO.builder()
                .id(jwtService.extractId(token))
                .email(jwtService.extractEmail(token))
                .roles(jwtService.extractRoles(token))
                .build();
    }

}
