package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.EducationDTO;
import ndmstartup.joinstartup.DTOs.PostEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.UniversityDTO;
import ndmstartup.joinstartup.Models.EmployeeEducation;
import ndmstartup.joinstartup.Repositories.DegreeTypeRepository;
import ndmstartup.joinstartup.Repositories.MajorRepository;
import ndmstartup.joinstartup.Repositories.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EducationMapper {

    private final UniversityRepository universityRepository;
    private final DegreeTypeRepository degreeTypeRepository;
    private final MajorRepository majorRepository;


    public EmployeeEducation postDtoToEntity(PostEmployeeEducationDTO educationDTO){
        return EmployeeEducation.builder()
                .university(universityRepository.findById(educationDTO.getUniversityId())
                        .orElseThrow(()->new NoSuchElementException("University not found")))
                .degreeType(degreeTypeRepository.findById(educationDTO.getDegreeTypeId())
                        .orElseThrow(()->new NoSuchElementException("Degree type not found")))
                .major(majorRepository.findById(educationDTO.getMajorId())
                        .orElseThrow(()->new NoSuchElementException("Major not found")))
                .beginningDate(educationDTO.getBeginningDate())
                .finishingDate(educationDTO.getFinishingDate())
                .build();
    }

    public EducationDTO entityToDto(EmployeeEducation education){
        return EducationDTO.builder()
                .educationId(education.getId())
                .major(education.getMajor().getName())
                .degreeType(education.getDegreeType().getName())
                .beginningDate(education.getBeginningDate())
                .finishingDate(education.getFinishingDate())
                .university(UniversityDTO.builder()
                        .name(education.getUniversity().getName())
                        .id(education.getUniversity().getId())
                        .Location(education.getUniversity().getLocation())
                        .build()).build();
    }

}
