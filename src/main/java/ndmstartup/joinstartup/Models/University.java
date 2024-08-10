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
@Table(name = "University")
@Getter
@Setter
@NoArgsConstructor
public class University {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50)
    private String name;

    @Column(name = "Location", nullable = false)
    @NotBlank(message = "Location is mandatory")
    @Size(max = 100)
    private String Location;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<EmployeeEducation> employeeEducations = new ArrayList<>();
}
