package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import ndmstartup.joinstartup.Services.Interfaces.StartUpService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/startUp")
public class StartUpController {

	private final StartUpService startUpService;
	private final ApplicationService applicationService;
	@GetMapping("/all")
	public ResponseEntity<List<GetStartUpDTO>> getAllStartUps(){
		List<GetStartUpDTO> startUps = startUpService.getAllStartUps();
		return ResponseEntity.ok(startUps);
	}

	@GetMapping("/{startUpId}")
	public ResponseEntity<GetStartUpDTO> getAllStartUps(@PathVariable Long startUpId){
		GetStartUpDTO startUp = startUpService.getStartUpByStartUpId(startUpId);
		return ResponseEntity.ok(startUp);
	}

	@GetMapping("/{startUpId}/employees")
	public ResponseEntity<GetStartUpIdEmployeeDTO> getAllEmployeesByStartUpId(@PathVariable Long startUpId){
		GetStartUpIdEmployeeDTO startUp = startUpService.getEmployeesByStartUpId(startUpId);
		return ResponseEntity.ok(startUp);
	}

	@GetMapping("/companyName/{companyName}/employees")
	public ResponseEntity<GetStartUpCompanyNameEmployeeDTO> getAllEmployeesByStartUpCompanyName(@PathVariable String companyName){
		GetStartUpCompanyNameEmployeeDTO startUp = startUpService.getEmployeesByStartUpCompanyName(companyName);
		return ResponseEntity.ok(startUp);
	}

	@GetMapping("/location/{location}/employees")
	public ResponseEntity<GetStartUpLocationEmployeeDTO> getAllEmployeesByStartUpLocation(@PathVariable String location){
		GetStartUpLocationEmployeeDTO startUp = startUpService.getEmployeesByStartUpLocation(location);
		return ResponseEntity.ok(startUp);
	}

	@GetMapping("/isPaid/{isPaid}/employees")
	public ResponseEntity<GetStartUpIsPaidEmployeeDTO> getAllEmployeesByStartUpIsPaid(@PathVariable boolean isPaid){
		GetStartUpIsPaidEmployeeDTO startUp = startUpService.getEmployeesByStartUpIsPaid(isPaid);
		return ResponseEntity.ok(startUp);
	}

	@GetMapping("/{startUpId}/applications")
	public ResponseEntity<List<GetApplicationDTO>> getApplications(
			@RequestParam(required = false) String applicationStatus,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date  startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, @PathVariable Long startUpId) {

		List<GetApplicationDTO> applications = applicationService.getApplicationsByStartUpAndCriteria(applicationStatus, startDate, endDate, startUpId);
		return ResponseEntity.ok(applications);
	}

}
