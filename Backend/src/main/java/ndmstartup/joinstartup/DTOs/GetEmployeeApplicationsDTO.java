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
public class GetEmployeeApplicationsDTO {

    private Long employeeId;
    private List<GetApplicationDTO> applications;

}
