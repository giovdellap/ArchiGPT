package it.uniroma1.commons.entity;

import it.uniroma1.commons.queue.enums.RoadType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "speed_cameras")
public class SpeedCamera {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "speedCamera")
    private List<Fine> fines = new ArrayList<>();

    @Column(name = "region")
    private String region;

    @Column(name = "road_type")
    private RoadType roadType;

}
