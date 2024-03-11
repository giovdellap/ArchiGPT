package it.uniroma1.commons.queue.object;

import it.uniroma1.commons.queue.enums.CarType;
import it.uniroma1.commons.queue.enums.FuelType;
import it.uniroma1.commons.queue.enums.RoadType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Detection {

    private Long id;

    private Long speedCameraId;

    private int speedValue;

    private String licensePlate;

    private String region;

    private RoadType roadType;
}
