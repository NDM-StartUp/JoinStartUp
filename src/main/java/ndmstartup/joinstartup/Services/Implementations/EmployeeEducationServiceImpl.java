package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;
import ndmstartup.joinstartup.Mappers.DegreeTypeMapper;
import ndmstartup.joinstartup.Mappers.MajorMapper;
import ndmstartup.joinstartup.Mappers.UniversityMapper;
import ndmstartup.joinstartup.Models.EmployeeEducation;
import ndmstartup.joinstartup.Repositories.EmployeeEducationRepository;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeEducationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeEducationServiceImpl implements EmployeeEducationService {
	private final EmployeeEducationRepository employeeEducationRepository;
	private final UniversityMapper universityMapper;
	private final MajorMapper majorMapper;
	private final DegreeTypeMapper degreeTypeMapper;

	@Override
	@Transactional
	public GetUniversityDTO getUniversityByEducationId(Long educationId) {
		EmployeeEducation education = employeeEducationRepository.findById(educationId)
				.orElseThrow(() -> new NoSuchElementException("EmployeeEducation not found with id: " + educationId));
		return universityMapper.entityToDTO(education.getUniversity());
	}

	@Override
	@Transactional
	public GetMajorDTO getMajorByEducationId(Long educationId) {
		EmployeeEducation education = employeeEducationRepository.findById(educationId)
				.orElseThrow(() -> new NoSuchElementException("EmployeeEducation not found with id: " + educationId));
		return majorMapper.entityToDTO(education.getMajor());
	}

	@Override
	@Transactional
	public GetDegreeTypeDTO getDegreeTypeByEducationId(Long educationId) {
		EmployeeEducation education = employeeEducationRepository.findById(educationId)
				.orElseThrow(() -> new NoSuchElementException("EmployeeEducation not found with id: " + educationId));
		return degreeTypeMapper.entityToDTO(education.getDegreeType());
	}

}
