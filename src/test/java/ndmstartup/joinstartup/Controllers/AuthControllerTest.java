package ndmstartup.joinstartup.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ndmstartup.joinstartup.DTOs.AuthDTO;
import ndmstartup.joinstartup.Exceptions.InvalidUsernameOrPasswordException;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Security.Services.Implementations.AuthServiceImpl;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthServiceImpl authService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void registerUser_Success() throws Exception {
        AuthDTO authDTO = new AuthDTO("lol@gmail.com", "Sefi_+");


        Mockito.doNothing().when(authService).register(authDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Registered"));

        Mockito.verify(authService, Mockito.times(1)).register(authDTO);
    }

    @Test
    void loginUser_Success() throws Exception {
        AuthDTO authDTO = new AuthDTO("lol@gmail.com", "Sefi_+");

        String mockToken = "jwt-token";
        Mockito.when(authService.verify(authDTO)).thenReturn(mockToken);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Loggeeed in"))
                .andExpect(header().string("Set-Cookie", "jwt=jwt-token; Path=/; HttpOnly"));

        Mockito.verify(authService, Mockito.times(1)).verify(authDTO);
    }

    @Test
    void loginUser_FailToLogin() throws Exception {
        AuthDTO authDTO = new AuthDTO("lol@gmail.com", "Sefi_+");

        Mockito.when(authService.verify(authDTO)).thenThrow(InvalidUsernameOrPasswordException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized());

        Mockito.verify(authService, Mockito.times(1)).verify(authDTO);
    }
}