package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetPositionDTO;
import ndmstartup.joinstartup.Services.Interfaces.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {

	private final PositionService positionService;

	@GetMapping("/all")
	public ResponseEntity<List<GetPositionDTO>> getAllPositions() {
		List<GetPositionDTO> positions = positionService.getAllPositions();
		return ResponseEntity.ok(positions);
	}
}
