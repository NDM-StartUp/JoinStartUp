package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

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
