package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Employee_Education")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEducation {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Employee_User_Id", nullable = false)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "University_Id", nullable = false)
	private University university;

	@ManyToOne
	@JoinColumn(name = "Major_Id", nullable = false)
	private Major major;

	@ManyToOne
	@JoinColumn(name = "Degree_Type_Id", nullable = false)
	private DegreeType degreeType;

	@Column(name = "Beginning_Date", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Beginning date cannot be null")
	private LocalDate beginningDate;

	@Column(name = "Finishing_Date")
	@Temporal(TemporalType.DATE)
	private LocalDate finishingDate;
}
