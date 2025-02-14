package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class User {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="First_Name", nullable = false)
	@NotBlank(message = "First name is mandatory")
	@Size(max = 50)
	private String firstName;

	@Column(name="Last_Name", nullable = false)
	@NotBlank(message = "Last name is mandatory")
	@Size(max = 50)
	private String lastName;

	@Column(name = "Email", nullable = false)
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid Email")
	private String email;

	@Column(name = "Phone")
	@Size(max =20)
	@Pattern(regexp = "\\+?[0-9\\-\\.\\(\\)\\s]+", message = "Invalid phone number")
	private String phone;

	@Column(name = "Description", nullable = false)
	@Size(max = 250)
	@NotBlank(message = "Description is mandatory")
	private String description;

	@Column(name = "Registration_Date", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private LocalDate registrationDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<LoginHistory> loginHistory = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserNotification> userNotifications = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true)
	private Employer employer;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true)
	private Employee employee;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Link> links = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SupportTicket> supportTickets = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "User_Status",
			joinColumns = @JoinColumn(name = "Status_Id"),
			inverseJoinColumns = @JoinColumn(name = "User_Id")
	)
	private List<Status> statuses = new ArrayList<>();
}
