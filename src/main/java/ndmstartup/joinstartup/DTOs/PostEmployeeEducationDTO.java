package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ndmstartup.joinstartup.Validations.Annotations.ValidDateRange;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@ValidDateRange(startDate = "beginningDate", endDate = "finishingDate"
        , message = "Finishing date must be after beginning date")
public class PostEmployeeEducationDTO {

        @NotNull(message = "University ID cannot be null")
        @Positive(message = "University ID must be a positive number")
        private Long universityId;

        @NotNull(message = "Major ID cannot be null")
        @Positive(message = "Major ID must be a positive number")
        private Long majorId;

        @NotNull(message = "Degree Type ID cannot be null")
        @Positive(message = "Degree Type ID must be a positive number")
        private Long degreeTypeId;

        @NotNull(message = "Beginning date cannot be null")
        @PastOrPresent(message = "Beginning date must be in the past or present")
        private Date beginningDate;

        @NotNull(message = "Finishing date cannot be null")
        private Date finishingDate;

}
