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
public class PostEmployeeExperienceDTO {

        private Position position;
        private String companyName;
        private Date startingDate;
        private Date endingDate;

}
