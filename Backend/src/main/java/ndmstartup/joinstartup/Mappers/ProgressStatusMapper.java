package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetProgressStatusDTO;
import ndmstartup.joinstartup.Models.ProgressStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProgressStatusMapper {
	public GetProgressStatusDTO entityToDTO(ProgressStatus progressStatus) {
		return GetProgressStatusDTO.builder()
				.id(progressStatus.getId())
				.name(progressStatus.getName())
				.build();
	}
}
