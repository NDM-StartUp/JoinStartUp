package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Repositories.StartUpStatusRepository;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import ndmstartup.joinstartup.Services.Interfaces.StartUpService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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

	@GetMapping("/search")
	public ResponseEntity<List<GetStartUpDTO>> searchStartUp(
			@RequestParam(required = false) Long startUpId,
			@RequestParam(required = false) String companyName,
			@RequestParam(required = false) String location,
			@RequestParam(required = false) Boolean isPaid) {

		List<GetStartUpDTO> startUps = startUpService.searchStartUpByCriteria(startUpId, companyName, location, isPaid);
		return ResponseEntity.ok(startUps);
	}

	@GetMapping("/{startUpId}/employees")
	public ResponseEntity<GetStartUpIdEmployeeDTO> getAllEmployeesByStartUpId(@PathVariable Long startUpId){
		GetStartUpIdEmployeeDTO startUp = startUpService.getEmployeesByStartUpId(startUpId);
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

	@PostMapping("/add")
	public ResponseEntity<Void> addStartUp(@RequestBody PostStartUpDTO postStartUpDTO){
		startUpService.addStartUp(postStartUpDTO);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{startUpId}/update")
	public ResponseEntity<Void> updateStartUp(@PathVariable Long startUpId,
											  @RequestBody PostStartUpDTO postStartUpDTO){
		startUpService.updateStartUp(startUpId, postStartUpDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{startUpId}/delete")
	public ResponseEntity<Void> deleteStartUp(@PathVariable Long startUpId){
		startUpService.deleteStartUp(startUpId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/by-progress-status")
	public ResponseEntity<List<GetStartUpDTO>> getStartUpsByProgressStatus(@RequestParam String progressStatusName) {
		List<GetStartUpDTO> startUps = startUpService.getStartUpsByProgressStatus(progressStatusName);
		return ResponseEntity.ok(startUps);
	}

	@PutMapping("/{startUpId}/status")
	public ResponseEntity<Void> updateStartUpStatus(
			@PathVariable Long startUpId,
			@RequestParam String progressStatusName) {

		startUpService.updateStartUpStatus(startUpId, progressStatusName);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{startUpId}/skills")
	public ResponseEntity<Void> deleteStartUpSkills(@PathVariable Long startUpId) {
		startUpService.deleteStartUpSkills(startUpId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{startUpId}/progress-statuses")
	public ResponseEntity<List<GetStartUpStatusDTO>> getAllStartUpProgressStatuses(@PathVariable Long startUpId) {
		List<GetStartUpStatusDTO> statuses = startUpService.getAllStartUpProgressStatuses(startUpId);
		return ResponseEntity.ok(statuses);
	}


/*	@GetMapping("/companyName/{companyName}/employees")
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
	}*/

}
