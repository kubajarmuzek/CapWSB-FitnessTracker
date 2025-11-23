package pl.wsb.fitnesstracker.workoutsession;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.Date;

@Entity
@Table(name = "workout_session")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "startLatitude", nullable = false)
    private double startLatitude;

    @Column(name = "startLongitude", nullable = false)
    private double startLongitude;

    @Column(name = "endLatitude", nullable = false)
    private double endLatitude;

    @Column(name = "endLongitude", nullable = false)
    private double endLongitude;

    @Column(name = "altitude", nullable = false)
    private double altitude;
}
