package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "User_Notification")
@Getter
@Setter
@NoArgsConstructor
public class UserNotification {
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "Timestamp cannot be null")
	private LocalDateTime date;

	@ManyToOne
	@JoinColumn(name = "Notification_Id", nullable = false)
	private Notification notification;

	@ManyToOne
	@JoinColumn(name = "User_Id", nullable = false)
	private User user;
}
