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
@Table(name = "Position")
@Getter
@Setter
@NoArgsConstructor
public class Position {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50)
    private String name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<WorkEmployee> employeePositions = new ArrayList<>();

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<StartUpEmployee> startUpTeam = new ArrayList<>();
}
