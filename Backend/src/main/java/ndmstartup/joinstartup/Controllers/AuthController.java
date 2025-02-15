package ndmstartup.joinstartup.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.AuthDTO;
import ndmstartup.joinstartup.DTOs.GetUserAuthInfoDTO;
import ndmstartup.joinstartup.DTOs.PostUserDTO;
import ndmstartup.joinstartup.DTOs.RegisterDTO;
import ndmstartup.joinstartup.Exceptions.InvalidUsernameOrPasswordException;
import ndmstartup.joinstartup.Security.Services.Interfaces.AuthService;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO user) {
        authService.register(AuthDTO.builder().email(user.getEmail()).password(user.getPassword()).build());
        userService.addUser(PostUserDTO.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .description(user.getDescription())
                .build(), false);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthDTO user, HttpServletResponse response) {
        String token = authService.login(user);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/verify")
    public ResponseEntity<GetUserAuthInfoDTO> verifyUser(HttpServletRequest request) {

        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new InvalidUsernameOrPasswordException("No token provided"));

         return ResponseEntity.ok(authService.getUserAuthInfo(token));
    }
}
