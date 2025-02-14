package ndmstartup.joinstartup.Controllers;

import ndmstartup.joinstartup.DTOs.GetPositionDTO;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Services.Interfaces.PositionService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PositionController.class)
@AutoConfigureMockMvc(addFilters = false)
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionService positionService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void getAllPositions_Success() throws Exception {
        List<GetPositionDTO> mockPositions = Arrays.asList(
                new GetPositionDTO(1L, "Developer"),
                new GetPositionDTO(2L, "Manager")
        );

        Mockito.when(positionService.getAllPositions()).thenReturn(mockPositions);

        mockMvc.perform(MockMvcRequestBuilders.get("/positions/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Developer"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Manager"));

        Mockito.verify(positionService, Mockito.times(1)).getAllPositions();
    }

    @Test
    void getAllPositions_ReturnsEmptyList() throws Exception {
        List<GetPositionDTO> mockPositions = new ArrayList<>();

        Mockito.when(positionService.getAllPositions()).thenReturn(mockPositions);

        mockMvc.perform(MockMvcRequestBuilders.get("/positions/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        Mockito.verify(positionService, Mockito.times(1)).getAllPositions();
    }
}