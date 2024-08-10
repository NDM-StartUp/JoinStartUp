package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Login_History")
@Getter
@Setter
@NoArgsConstructor
public class LoginHistory {
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "Login history date cannot be null")
	private Date date;

	@ManyToOne
	@JoinColumn(name = "User_Id", nullable = false)
	private User user;
}
