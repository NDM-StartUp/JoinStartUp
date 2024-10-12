package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Application_Cv")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationCv {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Employee_Cv_Id", nullable = false)
	private EmployeeCv employeeCv;

	@ManyToOne
	@JoinColumn(name = "Application_Status_Id", nullable = false)
	private ApplicationStatus status;

	@ManyToOne
	@JoinColumn(name = "StartUp_Id", nullable = false)
	private StartUp startUp;

	@Column(name = "Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "Application date cannot be null.")
	private LocalDateTime date;
}
