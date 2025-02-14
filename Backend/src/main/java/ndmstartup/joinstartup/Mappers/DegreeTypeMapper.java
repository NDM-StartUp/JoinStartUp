package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.Models.DegreeType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DegreeTypeMapper {

	public GetDegreeTypeDTO entityToDTO(DegreeType degreeType) {
		return GetDegreeTypeDTO.builder()
				.id(degreeType.getId())
				.name(degreeType.getName())
				.build();
	}

	public DegreeType dtoToEntity(GetDegreeTypeDTO degreeTypeDTO) {
		return DegreeType.builder()
				.id(degreeTypeDTO.getId())
				.name(degreeTypeDTO.getName())
				.build();
	}
}