package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Startup_Employee")
@Getter
@Setter
@NoArgsConstructor
public class StartUpEmployee {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "StartUp_Id", nullable = false)
	private StartUp startUp;

	@ManyToOne
	@JoinColumn(name = "Employee_Id", nullable = false)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "Position_Id", nullable = false)
	private Position position;

	@Column(name = "Starting_Date", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private LocalDate startingDate;

	@Column(name = "Ending_Date")
	@Temporal(TemporalType.DATE)
	private LocalDate endingDate;
}
