package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ndmstartup.joinstartup.Models.Position;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class GetExperienceDTO {

    private Long employeeId;
    private List<ExperienceDTO> experience;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class ExperienceDTO {
        private Long workId;
        private Position position;
        private String companyName;
        private Date startingDate;
        private Date endingDate;
    }

}
