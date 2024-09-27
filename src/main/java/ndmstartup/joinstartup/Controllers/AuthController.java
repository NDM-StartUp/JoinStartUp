package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Models.AppUserDetails;
import ndmstartup.joinstartup.Services.Interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AppUserDetails> registerUser(@RequestBody AppUserDetails user)
    {
        return ResponseEntity.ok(authService.register(user));
    }

}
