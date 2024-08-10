package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SupportTicket")
@Getter
@Setter
@NoArgsConstructor
public class SupportTicket {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name", nullable = false)
	@NotBlank
	@Size(max = 50)
	private String name;

	@Column(name = "Description", nullable = false)
	@NotBlank
	@Size(max = 250)
	private String description;

	@ManyToOne
	@JoinColumn(name = "User_Id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "Ticket_Status_Id", nullable = false)
	private TicketStatus ticketStatus;
}
