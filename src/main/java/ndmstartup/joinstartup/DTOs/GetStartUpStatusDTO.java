package ndmstartup.joinstartup.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetStartUpStatusDTO {
	private String statusName;
	private LocalDate date;
}
