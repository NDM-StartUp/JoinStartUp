package ndmstartup.joinstartup.Security.Services.Interfaces;

import ndmstartup.joinstartup.Models.AppUserDetails;

public interface AuthService {

    void register(AppUserDetails user);

    String verify(AppUserDetails user);
}
