package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetPositionDTO;
import ndmstartup.joinstartup.Models.Position;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PositionMapper {
	public GetPositionDTO entityToDTO (Position position) {
		return GetPositionDTO.builder()
				.id(position.getId())
				.name(position.getName())
				.build();
	}
}
