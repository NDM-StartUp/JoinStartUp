package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;
import ndmstartup.joinstartup.Models.University;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityMapper {

	public GetUniversityDTO entityToDTO(University university) {
		return GetUniversityDTO.builder()
				.id(university.getId())
				.name(university.getName())
				.location(university.getLocation())
				.build();
	}

	public University dtoToEntity(GetUniversityDTO universityDTO) {
		return University.builder()
				.id(universityDTO.getId())
				.name(universityDTO.getName())
				.location(universityDTO.getLocation())
				.build();
	}
}
