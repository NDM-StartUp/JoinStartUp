package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Void> addUser (
            @RequestBody PostUserDTO postUserDTO,
            @RequestParam boolean isEmployee
    ) {
        userService.addUser(postUserDTO, isEmployee);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @PostMapping("/{userId}/add-role")
    public ResponseEntity<Void> addUserRole (
            @PathVariable Long userId,
            @RequestParam boolean addEmployerRole
    ) {
        userService.addUserRole(userId, addEmployerRole);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @PutMapping("/{userId}/update")
    public ResponseEntity<Void> updateUserData (
            @PathVariable Long userId,
            @RequestBody PostUserDTO postUserDTO
    ) {
        userService.updateUserData(userId, postUserDTO);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Void> deleteUser ( @PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @GetMapping("/{userId}/user-types")
    public ResponseEntity<GetUserTypesDTO> getUserTypes (@PathVariable Long userId) {
        GetUserTypesDTO userTypes = userService.getUserTypes(userId);

        return ResponseEntity.ok(userTypes);
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @GetMapping("/{userId}/user-info")
    public ResponseEntity<GetUserInfoDTO> getUserInfo (@PathVariable Long userId) {
        GetUserInfoDTO getUserInfoDTO = userService.getUserInfo(userId);

        return ResponseEntity.ok(getUserInfoDTO);
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @GetMapping("/{userId}/login-history")
    public ResponseEntity<List<GetLoginHistoryDTO>> getLoginHistory (@PathVariable Long userId) {
        List<GetLoginHistoryDTO> loginHistory = userService.getLoginHistoryByUserId(userId);

        return ResponseEntity.ok(loginHistory);
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @PostMapping("/{userId}/add-status/{statusId}")
    public ResponseEntity<Void> addStatusForUser (
            @PathVariable Long userId,
            @PathVariable Long statusId
    ) {
        userService.addStatusForUserById(userId, statusId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @GetMapping("/{userId}/statuses")
    public ResponseEntity<List<GetUserStatusesDTO>> getUserStatuses (@PathVariable Long userId) {
        List<GetUserStatusesDTO> userStatuses = userService.getUserStatusesById(userId);

        return ResponseEntity.ok(userStatuses);
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @GetMapping("/{userId}/ticket")
    public ResponseEntity<List<GetSupportTicketDTO>> getSupportTicketByUserId(@PathVariable Long userId) {
        List<GetSupportTicketDTO> supportTicket = userService.getAllSupportTicketsByUserId(userId);
        return ResponseEntity.ok(supportTicket);
    }

    @PreAuthorize("#userId == authentication.principal.id ")
    @PostMapping("/{userId}/ticket")
    public ResponseEntity<GetSupportTicketDTO> addSupportTicket(@PathVariable Long userId,
                                                                @RequestBody PostSupportTicketDTO supportTicketDTO) {
        GetSupportTicketDTO supportTicket = userService.addSupportTicket(userId, supportTicketDTO);
        return ResponseEntity.ok(supportTicket);
    }

}
