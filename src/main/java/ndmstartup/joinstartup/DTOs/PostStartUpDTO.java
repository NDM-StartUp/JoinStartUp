package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostStartUpDTO {
	@NotBlank(message = "Name is mandatory")
	@Size(max = 50)
	private String name;

	@NotBlank(message = "Company name is mandatory")
	@Size(max = 50)
	private String companyName;

	@NotBlank(message = "Description is mandatory")
	@Size(max = 500)
	private String description;

	@NotBlank(message = "Requirements is mandatory")
	@Size(max = 200)
	private String requirements;

	@NotBlank(message = "Location is mandatory")
	@Size(max = 100)
	private String location;

	private boolean isPaid;
}
