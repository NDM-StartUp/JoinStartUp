package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.Models.AppUserDetails;

public interface AuthService {

    AppUserDetails register(AppUserDetails user);

}
