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
public class PostEmployeeEducationDTO {

        private Long universityId;
        private Long majorId;
        private Long degreeTypeId;
        private Date beginningDate;
        private Date finishingDate;

}
