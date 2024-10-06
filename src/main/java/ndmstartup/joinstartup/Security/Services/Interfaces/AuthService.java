package ndmstartup.joinstartup.Security.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.AuthDTO;

public interface AuthService {

    void register(AuthDTO user);

    String verify(AuthDTO user);
}
