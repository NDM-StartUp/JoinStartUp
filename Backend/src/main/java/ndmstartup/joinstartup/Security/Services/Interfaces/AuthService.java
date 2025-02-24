package ndmstartup.joinstartup.Security.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.AuthDTO;
import ndmstartup.joinstartup.DTOs.GetUserAuthInfoDTO;
import ndmstartup.joinstartup.DTOs.RegisterDTO;

public interface AuthService {

    void register(RegisterDTO user);
    String login(AuthDTO user);
    GetUserAuthInfoDTO getUserAuthInfo(String token);

}
