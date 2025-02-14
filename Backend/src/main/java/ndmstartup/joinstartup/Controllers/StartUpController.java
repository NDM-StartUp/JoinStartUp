package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationCvService;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import ndmstartup.joinstartup.Services.Interfaces.StartUpService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/startUp")
public class StartUpController {

	private final StartUpService startUpService;
	private final ApplicationService applicationService;
	private final ApplicationCvService applicationCvService;

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

	@PreAuthorize("@startUpSecurityServiceImpl.isOwnerOfStartUp(#startUpId, authentication.principal.id) || " +
			"@startUpSecurityServiceImpl.isEmployeeOfStartUp(#startUpId, authentication.principal.id)")
	@GetMapping("/{startUpId}/employees")
	public ResponseEntity<GetStartUpIdEmployeeDTO> getAllEmployeesByStartUpId(@PathVariable Long startUpId){
		GetStartUpIdEmployeeDTO startUp = startUpService.getEmployeesByStartUpId(startUpId);
		return ResponseEntity.ok(startUp);
	}

	@PreAuthorize("@startUpSecurityServiceImpl.isOwnerOfStartUp(#startUpId, authentication.principal.id)")
	@GetMapping("/{startUpId}/applications")
	public ResponseEntity<List<GetApplicationDTO>> getApplications(
			@RequestParam(required = false) String applicationStatus,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate, @PathVariable Long startUpId) {

		List<GetApplicationDTO> applications = applicationService.getApplicationsByStartUpAndCriteria(applicationStatus, startDate, endDate, startUpId);
		return ResponseEntity.ok(applications);
	}

	@PostMapping("/add")
	public ResponseEntity<Void> addStartUp(@RequestBody PostStartUpDTO postStartUpDTO){
		startUpService.addStartUp(postStartUpDTO);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("@startUpSecurityServiceImpl.isOwnerOfStartUp(#startUpId, authentication.principal.id)")
	@PutMapping("/{startUpId}/update")
	public ResponseEntity<Void> updateStartUp(@PathVariable Long startUpId,
											  @RequestBody PostStartUpDTO postStartUpDTO){
		startUpService.updateStartUp(startUpId, postStartUpDTO);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("@startUpSecurityServiceImpl.isOwnerOfStartUp(#startUpId, authentication.principal.id)")
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

	@PreAuthorize("@startUpSecurityServiceImpl.isOwnerOfStartUp(#startUpId, authentication.principal.id)")
	@PutMapping("/{startUpId}/status")
	public ResponseEntity<Void> updateStartUpStatus(
			@PathVariable Long startUpId,
			@RequestParam String progressStatusName) {

		startUpService.updateStartUpStatus(startUpId, progressStatusName);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("@startUpSecurityServiceImpl.isOwnerOfStartUp(#startUpId, authentication.principal.id)")
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


	@PreAuthorize("@startUpSecurityServiceImpl.isOwnerOfStartUp(#startUpId, authentication.principal.id)")
	@PutMapping("/{startUpId}/application/{applicationCvId}/status")
	public ResponseEntity<Void> updateApplicationStatus(
			@PathVariable Long applicationCvId,
			@RequestParam String applicationStatusName, @PathVariable Long startUpId) {

		applicationCvService.updateApplicationStatus(applicationCvId, applicationStatusName, startUpId);
		return ResponseEntity.noContent().build();
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
