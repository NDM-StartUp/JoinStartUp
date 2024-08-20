package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostUserDTO;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{userId}/add-role")
    public ResponseEntity<Void> addUserRole (
            @PathVariable Long userId,
            @RequestParam boolean addEmployerRole
    ) {
        userService.addUserRole(userId, addEmployerRole);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/ticket")
    public ResponseEntity<List<GetSupportTicketDTO>> getSupportTicketByUserId(@PathVariable Long userId) {
        List<GetSupportTicketDTO> supportTicket = userService.getAllSupportTicketsByUserId(userId);
        return ResponseEntity.ok(supportTicket);
    }

    @PostMapping("/{userId}/ticket")
    public ResponseEntity<GetSupportTicketDTO> addSupportTicket(@PathVariable Long userId,
                                                                @RequestBody PostSupportTicketDTO supportTicketDTO) {
        GetSupportTicketDTO supportTicket = userService.addSupportTicket(userId, supportTicketDTO);
        return ResponseEntity.ok(supportTicket);
    }

}
