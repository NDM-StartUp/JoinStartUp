package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class GetStartUpIsPaidEmployeeDTO {
	private Long startUpId;
	private boolean isPaid;
	private List<EmployeeDTO> employeeDTOList;
	@Data
	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@AllArgsConstructor
	public static class EmployeeDTO {
		private Long employeeId;
		private UserDTO user;
	}

	@Data
	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@AllArgsConstructor
	public static class UserDTO {
		private String firstName;
		private String lastName;
		private String email;
		private String phone;
	}
}
