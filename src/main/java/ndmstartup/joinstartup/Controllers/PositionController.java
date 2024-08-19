package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetPositionDTO;
import ndmstartup.joinstartup.Services.Implementations.PositionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {

	private final PositionServiceImpl positionService;

	@GetMapping("/all")
	public ResponseEntity<List<GetPositionDTO>> getAllPositions() {
		List<GetPositionDTO> positions = positionService.getAllPositions();
		return ResponseEntity.ok(positions);
	}
}
