package javastatistics;


import it.uniroma1.commons.queue.enums.RoadType;
import it.uniroma1.commons.queue.object.DetectionExt;

import java.util.LinkedList;

public class JavaSupport {
    public static void executeQueries(LinkedList<DetectionExt> detections){

        System.out.println("Autovelox più attivo " + JavaUtils.mostActiveSpeedCamera(detections));

        LinkedList hs = JavaUtils.highestSpeed(detections);
        Long hs1 = (Long)hs.get(0);
        Integer hs2 = (Integer)hs.get(1);
        System.out.println("Velocità più alta raggiunta " + hs2+ " km/h da autovelox "+ hs1);

        System.out.println("Regione più trafficata " + JavaUtils.busiestRegion(detections));

        System.out.println("Percentuale di veicoli eco-friendly (elettrici o ibridi): " + JavaUtils.ecoFriendlyPercentage(detections) * 100 + "%");

        System.out.println("Regione con più multe: " + JavaUtils.mostCriminalRegion(detections));

        LinkedList hasc = JavaUtils.highestAverageSpeedCamera(detections);
        Long hasc1 = (Long)hasc.get(0);
        Integer hasc2 = (Integer) hasc.get(1);
        System.out.println("Autovelox con la media di velocità registrate più alta: " + hasc1 + ", " + hasc2+" km/h");

        LinkedList har = JavaUtils.highestAverageRegion(detections);
        String har1 = (String)har.get(0);
        Double har2 = (Double) har.get(1);
        System.out.println("Regione con la percentuale più alta di rilevazioni sopra il limite di velocità: " + har1 + ", " + har2 * 100 + "%");

        System.out.println("Alimentazione più diffusa: " + JavaUtils.mostUsedFuel(detections));

        System.out.println("Veicolo più diffuso: " + JavaUtils.mostUsedCarType(detections));

        LinkedList hacr = JavaUtils.highestAverageCriminalRoad(detections);
        RoadType hacr1 = (RoadType) hacr.get(0);
        Double hacr2 = (Double) hacr.get(1);
        System.out.println("Tipo di strada con percentuale più alta di rilevazioni sopra il limite: " + hacr1 + ", " + hacr2 * 100 + "%");








    }




}
