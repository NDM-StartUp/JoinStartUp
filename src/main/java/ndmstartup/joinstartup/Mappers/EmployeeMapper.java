package ndmstartup.joinstartup.Mappers;

import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;
import ndmstartup.joinstartup.Models.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {

    public GetEmployeeExperienceDTO entityToExperienceDTO(Employee employee) {

        return GetEmployeeExperienceDTO.builder()
                .employeeId(employee.getId())
                .experience(employee.getEmployeePositions().stream().map((experience-> GetEmployeeExperienceDTO.ExperienceDTO.builder()
                        .workId(experience.getId())
                        .companyName(experience.getCompanyName())
                        .position(experience.getPosition())
                        .startingDate(experience.getStartingDate())
                        .endingDate(experience.getEndingDate())
                        .build())).toList())
                        .build();


    }

    public GetEmployeeEducationDTO entityToEducationDTO(Employee employee) {

        return GetEmployeeEducationDTO.builder()
                .employeeId(employee.getId())
                .educationList(employee.getEmployeeEducations().stream().map((education -> GetEmployeeEducationDTO.EducationDTO.builder()
                        .educationId(education.getId())
                        .major(education.getMajor().getName())
                        .degreeType(education.getDegreeType().getName())
                        .beginningDate(education.getBeginningDate())
                        .finishingDate(education.getFinishingDate())
                        .university(GetEmployeeEducationDTO.UniversityDTO.builder()
                                .name(education.getUniversity().getName())
                                .id(education.getUniversity().getId())
                                .Location(education.getUniversity().getLocation())
                                .build()).build())).toList())
                .build();

    }

}
