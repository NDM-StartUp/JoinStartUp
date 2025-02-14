package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.Models.Major;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MajorMapper {

	public GetMajorDTO entityToDTO(Major major) {
		return GetMajorDTO.builder()
				.id(major.getId())
				.name(major.getName())
				.build();
	}

	public Major dtoToEntity(GetMajorDTO majorDTO) {
		return Major.builder()
				.id(majorDTO.getId())
				.name(majorDTO.getName())
				.build();
	}
}
