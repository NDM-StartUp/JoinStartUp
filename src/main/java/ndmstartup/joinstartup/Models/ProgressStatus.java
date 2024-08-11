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
@Table(name = "Progress_Status")
@Getter
@Setter
@NoArgsConstructor
public class ProgressStatus {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 25)
    private String name;

    @OneToMany(mappedBy = "progressStatus", cascade = CascadeType.ALL)
    private List<StartUpStatus> startUpStatus = new ArrayList<>();

}
