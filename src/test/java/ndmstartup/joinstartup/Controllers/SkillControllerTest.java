package ndmstartup.joinstartup.Controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndmstartup.joinstartup.DTOs.GetSkillDTO;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Services.Interfaces.SkillService;
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

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SkillController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SkillControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SkillService skillService;

	@MockBean
	private JwtFilter jwtFilter;


	@Test
	void getAllSkills_Success() throws Exception {
		List<GetSkillDTO> skills = List.of(
				GetSkillDTO.builder().id(1L).name("Java").build(),
				GetSkillDTO.builder().id(2L).name("Python").build()
		);

		Mockito.when(skillService.getAllSkills()).thenReturn(skills);

		mockMvc.perform(MockMvcRequestBuilders.get("/skills/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Java"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Python"));

		Mockito.verify(skillService).getAllSkills();
	}

	@Test
	void getAllSkills_EmptyList() throws Exception {
		Mockito.when(skillService.getAllSkills()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/skills/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isEmpty());

		Mockito.verify(skillService).getAllSkills();
	}
}
