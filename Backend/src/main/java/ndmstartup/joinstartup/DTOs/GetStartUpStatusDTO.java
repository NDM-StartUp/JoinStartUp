package ndmstartup.joinstartup.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class GetStartUpStatusDTO {
	private String statusName;
	private LocalDate date;
}
