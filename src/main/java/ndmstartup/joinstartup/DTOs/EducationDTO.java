package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class EducationDTO {
    private Long educationId;
    private UniversityDTO university;
    private String major;
    private String degreeType;
    private Date beginningDate;
    private Date finishingDate;
}
