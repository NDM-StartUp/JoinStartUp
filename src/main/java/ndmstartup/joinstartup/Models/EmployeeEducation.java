package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Employee_Education")
@Getter
@Setter
@NoArgsConstructor
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
	private Date beginningDate;

	@Column(name = "Finishing_Date")
	@Temporal(TemporalType.DATE)
	private Date finishingDate;
}
