package it.uniroma1.commons.queue.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@AllArgsConstructor
@Getter
public enum RoadType {
    THROUGHWAY(110),
    HIGHWAY(130),
    CITY_ROAD(50);

    private final int threshold;

    public static RoadType getRandomRoadType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
