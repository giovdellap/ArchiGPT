package javastatistics;

import it.uniroma1.commons.entity.Car;
import it.uniroma1.commons.entity.SpeedCamera;
import it.uniroma1.commons.queue.enums.CarType;
import it.uniroma1.commons.queue.enums.FuelType;
import it.uniroma1.commons.queue.enums.RoadType;
import it.uniroma1.commons.queue.object.DetectionExt;
//import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;

public class JavaUtils {
    public static Long mostActiveSpeedCamera(LinkedList<DetectionExt> list){ //DOVREBBE ANDARE
        HashMap<Long, Integer> speedCameraMap = new HashMap<Long, Integer>();
        Long actualSpeedMax = null;
        int actualMax = 0;
        for (DetectionExt detection: list){
            Integer oldValue = speedCameraMap.get(detection.getSpeedCameraId());
            if(oldValue==null)
                oldValue = 0;
            speedCameraMap.put(detection.getSpeedCameraId(), oldValue+1);
            if(actualMax < oldValue) {
                actualSpeedMax = detection.getSpeedCameraId();
                actualMax = oldValue;
            }
        }
        return actualSpeedMax;
    }
    public static LinkedList highestSpeed(LinkedList<DetectionExt> list){  //DOVREBBE ANDARE
        int actualMax = 0;
        Long actualSpeedMax = null;
        for (DetectionExt detectionExt: list){
            if(detectionExt.getSpeedValue()>actualMax){
                actualMax = detectionExt.getSpeedValue();
                actualSpeedMax = detectionExt.getSpeedCameraId();
            }

        }
        LinkedList ret = new LinkedList();
        ret.add(actualSpeedMax);
        ret.add(actualMax);
        return ret;
    }
    public static LinkedList highestAverageSpeedCamera(LinkedList<DetectionExt> list){//DOVREBBE ANDARE
        HashMap<Long, LinkedList> speedCameraMap = new HashMap<Long, LinkedList>();
        for (DetectionExt detection: list){
            LinkedList oldValue = speedCameraMap.get(detection.getSpeedCameraId());
            Integer oldNumber = 0;
            Integer oldSum = 0;
            if(oldValue!=null){
                oldNumber = (Integer) oldValue.get(1);
                oldSum = (Integer) oldValue.get(0);
            }
            LinkedList newValue = new LinkedList<>();
            newValue.addLast(oldSum + detection.getSpeedValue());
            newValue.addLast(oldNumber + 1);
            speedCameraMap.put(detection.getSpeedCameraId(), newValue);

        }
        Long actualSpeedCamera = null;
        int actualMax = 0;
        for (Long sp: speedCameraMap.keySet()){
            LinkedList value = speedCameraMap.get(sp);
            Integer sum = (Integer) value.get(0);
            Integer number = (Integer) value.get(1);
            Integer average = sum/number;
            if(average >= actualMax){
                actualMax = average;
                actualSpeedCamera = sp;
            }
        }
        LinkedList ret = new LinkedList();
        ret.add(actualSpeedCamera);
        ret.add(actualMax);
        return ret;
    }


    public static String busiestRegion(LinkedList<DetectionExt> list){ //DOVREBBE ANDARE
        HashMap<String, Integer> speedCameraMap = new HashMap<String, Integer>();
        String actualRegion = null;
        int actualMax = 0;
        for (DetectionExt detection: list){
            Integer oldValue = speedCameraMap.get(detection.getRegion());
            if(oldValue==null)
                oldValue = 0;
            speedCameraMap.put(detection.getRegion(), oldValue+1);
            if(actualMax < oldValue) {
                actualRegion = detection.getRegion();
                actualMax = oldValue;
            }
        }
        return actualRegion;

    }

    public static Double ecoFriendlyPercentage(LinkedList<DetectionExt> list){ //DOVREBBE ANDARE
        int ecoFriendlyNumber = 0;

        for (DetectionExt detection: list){
            FuelType fuelType = detection.getFuelType();
            if(fuelType == FuelType.HYBRID || fuelType == FuelType.FULL_ELECTRIC)
                ecoFriendlyNumber = ecoFriendlyNumber +1;
        }
        Double ecoPercentage = Double.valueOf(ecoFriendlyNumber) / Double.valueOf(list.size());
        return ecoPercentage;
    }
    public static FuelType mostUsedFuel(LinkedList<DetectionExt> list){
        HashMap<FuelType, Integer> speedCameraMap = new HashMap<FuelType, Integer>();
        FuelType actualFuelTypeMax = null;
        Integer actualMax = 0;
        for (DetectionExt detection: list){
            Integer oldValue = speedCameraMap.get(detection.getFuelType());
            if(oldValue==null)
                oldValue = 0;
            speedCameraMap.put(detection.getFuelType(), oldValue+1);
            if(actualMax < oldValue){
                actualFuelTypeMax = detection.getFuelType();
                actualMax = oldValue;
            }
        }
        return actualFuelTypeMax;
    }

    public static String mostCriminalRegion(LinkedList<DetectionExt> list){ //DOVREBBE ANDARE
        HashMap<String, Integer> speedCameraMap = new HashMap<String, Integer>();
        String actualRegion = null;
        int actualMax = 0;
        for (DetectionExt detection: list){
            if(detection.isOvercameLimit()) {
                Integer oldValue = speedCameraMap.get(detection.getRegion());
                if (oldValue == null)
                    oldValue = 0;



                speedCameraMap.put(detection.getRegion(), oldValue + 1);
                if (actualMax < oldValue){
                    actualRegion = detection.getRegion();
                    actualMax = oldValue;
                }
            }
        }
        return actualRegion;
    }

    public static LinkedList highestAverageRegion(LinkedList<DetectionExt> list){
        //regione con percentuale di rilevazioni sopra il limite di velocità più alto
        HashMap<String, LinkedList> speedCameraMap = new HashMap<String, LinkedList>();
        for (DetectionExt detection: list){
            LinkedList oldValue = speedCameraMap.get(detection.getRegion());
            Integer oldOverlimit = 0;
            Integer oldTotal = 0;
            if(oldValue!=null){
                oldOverlimit = (Integer) oldValue.get(1);
                oldTotal = (Integer) oldValue.get(0);
            }
            LinkedList newValue = new LinkedList<>();
            oldTotal = oldTotal + 1;

            if(detection.isOvercameLimit())
                oldOverlimit = oldOverlimit + 1;

            newValue.addLast(oldTotal );
            newValue.addLast(oldOverlimit);
            speedCameraMap.put(detection.getRegion(), newValue);

        }
        String actualRegion = null;
        Double actualMax = Double.valueOf(0);
        for (String region: speedCameraMap.keySet()){
            LinkedList value = speedCameraMap.get(region);
            Integer total = (Integer) value.get(0);
            Integer overlimit = (Integer) value.get(1);
            Double percentage = Double.valueOf(overlimit)/Double.valueOf(total);
            if(percentage >= actualMax){
                actualMax = percentage;
                actualRegion = region;
            }
        }
        LinkedList ret = new LinkedList();
        ret.add(actualRegion);
        ret.add(actualMax);
        return ret;
    }



    public static CarType mostUsedCarType(LinkedList<DetectionExt> list){
        HashMap<CarType, Integer> speedCameraMap = new HashMap<CarType, Integer>();
        CarType actualCarTypeMax = null;
        Integer actualMax = 0;
        for (DetectionExt detection: list){
            Integer oldValue = speedCameraMap.get(detection.getCarType());
            if(oldValue==null)
                oldValue = 0;
            speedCameraMap.put(detection.getCarType(), oldValue+1);
            if(actualMax < oldValue){
                actualCarTypeMax = detection.getCarType();
                actualMax = oldValue;
            }
        }
        return actualCarTypeMax;

    }

    public static LinkedList highestAverageCriminalRoad(LinkedList<DetectionExt> list) {
        //tipo di strada con media di rilevazioni sopra il limite più alta
        //regione con percentuale di rilevazioni sopra il limite di velocità più alto
        HashMap<RoadType, LinkedList> speedCameraMap = new HashMap<RoadType, LinkedList>();
        for (DetectionExt detection: list){
            LinkedList oldValue = speedCameraMap.get(detection.getRoadType());
            Integer oldOverlimit = 0;
            Integer oldTotal = 0;
            if(oldValue!=null){
                oldOverlimit = (Integer) oldValue.get(1);
                oldTotal = (Integer) oldValue.get(0);
            }
            LinkedList newValue = new LinkedList<>();
            oldTotal = oldTotal + 1;
            if(detection.isOvercameLimit())
                oldOverlimit = oldOverlimit + 1;
            newValue.addLast(oldTotal);
            newValue.addLast(oldOverlimit);
            speedCameraMap.put(detection.getRoadType(), newValue);

        }
        RoadType actualRoad = null;
        Double actualMax = Double.valueOf(0);
        for (RoadType road: speedCameraMap.keySet()){
            LinkedList value = speedCameraMap.get(road);
            Integer total = (Integer) value.get(0);
            Integer overlimit = (Integer) value.get(1);
            Double percentage = Double.valueOf(overlimit)/Double.valueOf(total);
            if(percentage >= actualMax){
                actualMax = percentage;
                actualRoad = road;
            }
        }
        LinkedList ret = new LinkedList();
        ret.add(actualRoad);
        ret.add(actualMax);
        return ret;


    }


}
