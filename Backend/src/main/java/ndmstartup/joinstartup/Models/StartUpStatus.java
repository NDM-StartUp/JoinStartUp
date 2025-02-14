package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "StartUp_Status")
@Getter
@Setter
@NoArgsConstructor
public class StartUpStatus {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="StartUp_Id", nullable=false)
    private StartUp startUp;

    @ManyToOne
    @JoinColumn(name="Progress_Status_Id", nullable=false)
    private ProgressStatus progressStatus;

    @Column(name = "Date", nullable = false)
    @NotNull(message = "Date is mandatory")
    private LocalDate date;

}
