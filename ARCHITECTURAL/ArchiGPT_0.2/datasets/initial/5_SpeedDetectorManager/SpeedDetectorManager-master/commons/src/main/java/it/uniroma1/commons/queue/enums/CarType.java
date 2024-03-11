package it.uniroma1.commons.queue.enums;

import java.util.Random;

public enum CarType {
    MICRO,
    SUV,
    VAN,
    COUPE,
    SUPERCAR,
    CAMPER,
    TRUCK;

    public static CarType getRandomCarType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
