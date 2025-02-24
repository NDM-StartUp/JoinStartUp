package ndmstartup.joinstartup.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.AuthDTO;
import ndmstartup.joinstartup.DTOs.GetUserAuthInfoDTO;
import ndmstartup.joinstartup.DTOs.RegisterDTO;
import ndmstartup.joinstartup.Exceptions.InvalidUsernameOrPasswordException;
import ndmstartup.joinstartup.Security.Services.Interfaces.AuthService;
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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDTO user) {
        authService.register(user);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody AuthDTO user, HttpServletResponse response) {
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
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new InvalidUsernameOrPasswordException("No token provided");
        }
        String token = Arrays.stream(cookies)
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new InvalidUsernameOrPasswordException("No token provided"));

         return ResponseEntity.ok(authService.getUserAuthInfo(token));
    }
}
