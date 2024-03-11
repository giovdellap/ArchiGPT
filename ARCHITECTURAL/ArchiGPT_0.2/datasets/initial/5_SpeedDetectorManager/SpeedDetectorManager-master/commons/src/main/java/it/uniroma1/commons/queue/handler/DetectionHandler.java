//package it.uniroma1.commons.queue.handler;
//
//import it.uniroma1.commons.entity.Fine;
//import it.uniroma1.commons.entity.SpeedCamera;
//import it.uniroma1.commons.queue.object.Detection;
//import it.uniroma1.commons.repository.FineRepository;
//import it.uniroma1.commons.repository.SpeedCameraRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.net.ssl.SSLException;
//import java.util.Optional;
//
//@AllArgsConstructor
//@Service
//public class DetectionHandler {
//    private FineRepository fineRepository;
//
//    private SpeedCameraRepository speedCameraRepository;
//
//    public void handle(Detection detection) throws SSLException {
//        Fine fine =  new Fine();
//
//        //search SpeedCamera on DB
//        Optional<SpeedCamera> optionalSpeedCamera = speedCameraRepository.findById(detection.getSpeedCameraId());
//        if(optionalSpeedCamera.isEmpty()) throw new SSLException("SpeedCamera is not present");
//
////        fine.setSpeedCamera(optionalSpeedCamera.get());
//        fine.setCarPlate(detection.getLicensePlate());
//        //finire con la gestione della multa
//
//        fineRepository.save(fine);
//    }
//}
