package ndmstartup.joinstartup.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import ndmstartup.joinstartup.DTOs.AuthDTO;
import ndmstartup.joinstartup.DTOs.GetUserAuthInfoDTO;
import ndmstartup.joinstartup.DTOs.RegisterDTO;
import ndmstartup.joinstartup.Exceptions.InvalidUsernameOrPasswordException;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Security.Services.Implementations.JWTService;
import ndmstartup.joinstartup.Security.Services.Interfaces.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JWTService jwtService;

    @Test
    void registerUser_Success() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("asd@gmail.com", "123456A-", "ASD", "LOL","123123213", "asdsdgfds gdfg dfg");


        Mockito.doNothing().when(authService).register(registerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Registered"));

        Mockito.verify(authService, Mockito.times(1)).register(registerDTO);
    }

    @Test
    void loginUser_Success() throws Exception {
        AuthDTO authDTO = new AuthDTO("lol@gmail.com", "Seeffi_+");

        String mockToken = "jwt-token";
        Mockito.when(authService.login(authDTO)).thenReturn(mockToken);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in"))
                .andExpect(header().string("Set-Cookie", "jwt=jwt-token; Path=/; HttpOnly"));

        Mockito.verify(authService, Mockito.times(1)).login(authDTO);
    }

    @Test
    void loginUser_FailToLogin() throws Exception {
        AuthDTO authDTO = new AuthDTO("lol@gmail.com", "Seeffi_+");

        Mockito.when(authService.login(authDTO)).thenThrow(InvalidUsernameOrPasswordException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized());

        Mockito.verify(authService, Mockito.times(1)).login(authDTO);
    }

    @Test
    void logoutUser_Success() throws Exception {
        AuthDTO authDTO = new AuthDTO("lol@gmail.com", "Seeffi_+");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged out"))
                .andExpect(header().string("Set-Cookie", "jwt=; Path=/; HttpOnly"));
    }

    @Test
    void verify_Success() throws Exception {
        String mockToken = "jwt-token";
        GetUserAuthInfoDTO authDTO = new GetUserAuthInfoDTO(1L, "lol@gmail.com", Set.of("ROLE_USER"));

        Mockito.when(authService.getUserAuthInfo(mockToken)).thenReturn(authDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/verify")
                        .cookie(new Cookie("jwt", mockToken)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(authDTO)));

    }

    @Test
    void verify_FailToVerify_CookieIsMissing() throws Exception {
        String mockToken = "jwt-token";
        GetUserAuthInfoDTO authDTO = new GetUserAuthInfoDTO(1L, "lol@gmail.com", Set.of("ROLE_USER"));

        Mockito.when(authService.getUserAuthInfo(mockToken)).thenReturn(authDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/verify"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void verify_FailToVerify_JWTIsMissing() throws Exception {
        String mockToken = "jwt-token";
        GetUserAuthInfoDTO authDTO = new GetUserAuthInfoDTO(1L, "lol@gmail.com", Set.of("ROLE_USER"));

        Mockito.when(authService.getUserAuthInfo(mockToken)).thenReturn(authDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/verify")
                        .cookie(new Cookie("notJWT", mockToken)))
                .andExpect(status().isUnauthorized());
    }
}