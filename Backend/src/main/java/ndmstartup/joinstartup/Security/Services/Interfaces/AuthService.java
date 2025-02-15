package ndmstartup.joinstartup.Security.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.AuthDTO;
import ndmstartup.joinstartup.DTOs.GetUserAuthInfoDTO;

public interface AuthService {

    void register(AuthDTO user);
    String login(AuthDTO user);
    GetUserAuthInfoDTO getUserAuthInfo(String token);

}
