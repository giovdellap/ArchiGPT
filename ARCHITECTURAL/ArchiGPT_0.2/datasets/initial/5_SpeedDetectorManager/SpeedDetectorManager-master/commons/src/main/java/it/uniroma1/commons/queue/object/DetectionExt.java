package it.uniroma1.commons.queue.object;

import it.uniroma1.commons.queue.enums.CarType;
import it.uniroma1.commons.queue.enums.FuelType;
import it.uniroma1.commons.queue.enums.RoadType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetectionExt {

    private Long id;

    private Long speedCameraId;

    private int speedValue;

    private String licensePlate;

    private CarType carType;

    private FuelType fuelType;

    private String region;

    private RoadType roadType;

    private boolean overcameLimit;

    public DetectionExt() {
    }

    public DetectionExt(Long i, Long sc, int v, String l, CarType t, FuelType f, String r, RoadType rt, boolean o) {
        id = i;
        speedCameraId = sc;
        speedValue = v;
        licensePlate = l;
        carType = t;
        fuelType = f;
        region = r;
        roadType = rt;
        overcameLimit = o;
    }

    @Override
    public String toString() {
        String unknownType = "SCONOSCIUTO";
        return "[\"" + id.toString() + "\", \"" + speedCameraId.toString() + "\", \"" + speedValue + "\", \"" +
                licensePlate + "\", \"" + ((carType!=null)?carType.toString():unknownType)+ "\", \"" + ((fuelType!=null)?fuelType.toString():unknownType) + "\", \"" + region + "\", \"" +
                roadType.toString() + "\", \"" + overcameLimit +
                "\"]";
    }
}

