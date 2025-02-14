package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Notification")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="Name", nullable = false)
	@NotBlank(message = "Name is mandatory")
	@Size(max = 50)
	private String Name;

	@Column(name = "Description", nullable = false)
	@Size(max = 250)
	@NotBlank(message = "Description is mandatory")
	private String description;

	@ManyToOne
	@JoinColumn(name = "Notification_Type", nullable = false)
	private NotificationType notificationType;

	@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
	private List<UserNotification> userNotifications = new ArrayList<>();
}
