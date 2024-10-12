package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class GetEmployeeExperienceDTO {

    private Long employeeId;
    private List<ExperienceDTO> experience;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class ExperienceDTO {
        private Long workId;
        @ManyToOne(fetch = FetchType.LAZY)
        private GetPositionDTO position;
        private String companyName;
        private LocalDate startingDate;
        private LocalDate endingDate;
    }

}
