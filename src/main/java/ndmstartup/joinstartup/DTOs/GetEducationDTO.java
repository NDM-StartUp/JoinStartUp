package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class GetEducationDTO {

    private Long employeeId;
    private List<EducationDTO> educationList;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class EducationDTO {
        private Long educationId;
        private UniversityDTO university;
        private String major;
        private String degreeType;
        private Date beginningDate;
        private Date finishingDate;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class UniversityDTO {
        private Long id;
        private String name;
        private String Location;
    }

}
