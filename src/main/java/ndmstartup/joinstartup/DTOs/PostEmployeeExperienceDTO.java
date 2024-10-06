package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ndmstartup.joinstartup.Models.Position;
import ndmstartup.joinstartup.Validations.Annotations.ValidDateRange;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@ValidDateRange(startDate = "startingDate", endDate = "endingDate"
        , message = "Finishing date must be after beginning date")
public class PostEmployeeExperienceDTO {

        private Position position;

        @NotBlank(message = "Company name cannot be empty")
        private String companyName;

        @NotNull(message = "Starting date cannot be null")
        @PastOrPresent(message = "Starting date must be in the past or present")
        private Date startingDate;

        private Date endingDate;

}
