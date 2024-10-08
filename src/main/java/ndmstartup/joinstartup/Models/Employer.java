package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Employer")
@Getter
@Setter
@NoArgsConstructor
public class Employer {
	@Id
	@Column(name = "User_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "User_id", nullable = false)
	private User user;

	@ManyToMany(mappedBy = "employers")
	private List<StartUp> startUps = new ArrayList<>();
}
