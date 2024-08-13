package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "SupportTicket")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SupportTicket {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name", nullable = false)
	@NotBlank(message = "Name is mandatory")
	@Size(max = 50)
	private String name;

	@Column(name = "Description", nullable = false)
	@NotBlank(message = "Description is mandatory")
	@Size(max = 250)
	private String description;

	@ManyToOne
	@JoinColumn(name = "User_Id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "Ticket_Status_Id", nullable = false)
	private TicketStatus ticketStatus;
}
