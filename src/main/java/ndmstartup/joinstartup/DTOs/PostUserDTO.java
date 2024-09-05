package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDTO {

	@NotBlank(message = "First name is mandatory")
	@Size(max = 50)
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	@Size(max = 50)
	private String lastName;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid Email")
	private String email;

	@NotBlank(message = "Phone number is mandatory")
	@Size(max =20)
	@Pattern(regexp = "\\+?[0-9\\-.()\\s]+", message = "Invalid phone number")
	private String phone;

	@NotBlank(message = "Description is mandatory")
	@Size(max = 250)
	private String description;

}
