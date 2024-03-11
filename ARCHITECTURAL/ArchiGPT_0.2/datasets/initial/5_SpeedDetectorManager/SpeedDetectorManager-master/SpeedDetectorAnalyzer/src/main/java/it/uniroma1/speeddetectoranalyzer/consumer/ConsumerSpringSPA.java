package it.uniroma1.speeddetectoranalyzer.consumer;

import com.google.gson.Gson;
import it.uniroma1.commons.entity.Car;
import it.uniroma1.commons.entity.SpeedCamera;
import it.uniroma1.commons.queue.enums.CarType;
import it.uniroma1.commons.queue.enums.RoadType;
import it.uniroma1.commons.queue.handler.MessageHandler;
import it.uniroma1.commons.queue.object.Detection;
import it.uniroma1.commons.queue.object.DetectionExt;
import it.uniroma1.commons.repository.CarRepository;
import it.uniroma1.commons.repository.FineRepository;
import it.uniroma1.commons.repository.SpeedCameraRepository;
import it.uniroma1.commons.utility.ParamConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.Optional;

@Component
public class ConsumerSpringSPA {
	@Autowired
	@Qualifier("statistic_queue")
	private Queue statisticQueue;

	@Autowired
	@Qualifier("speed_queue")
	private Queue speedDetectionQueue;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	CarRepository carRepository;

	@Autowired
	SpeedCameraRepository speedCameraRepository;

	MessageHandler messageHandler = new MessageHandler();
	private static final Logger logger = LoggerFactory.getLogger(ConsumerSpringSPA.class);

	@JmsListener(destination = ParamConnection.DETECTION_QUEUE)
	public void consumeMessage(String message) {
		boolean overcameLimit = false;
		logger.info("Message received from activemq queue---" + message);
		try {
			MessageHandler messageHandler = new MessageHandler();
			Gson gson = new Gson();
			//gestione della rilevazione ed inoltra alla coda opportuna
			Detection detection = (Detection) messageHandler.handleMessage(message, Detection.class);
			SpeedCamera speedCamera = speedCameraRepository.findById(detection.getSpeedCameraId()).orElse(null);
			if (speedCamera != null) {
				detection.setRegion(speedCamera.getRegion());
			}
			if (detection.getRoadType() == RoadType.HIGHWAY && detection.getSpeedValue() > RoadType.HIGHWAY.getThreshold() ||
					detection.getRoadType() == RoadType.THROUGHWAY && detection.getSpeedValue() > RoadType.THROUGHWAY.getThreshold() ||
					detection.getRoadType() == RoadType.CITY_ROAD && detection.getSpeedValue() > RoadType.CITY_ROAD.getThreshold()) {

				String json = gson.toJson(detection);
				//mando a speedDetection
				jmsTemplate.convertAndSend(speedDetectionQueue, json);
				overcameLimit = true;
				System.out.println("Sent message to SpeedDetection");
			}

			//mando a statistics
			DetectionExt detectionExt = new DetectionExt();
			//setto i valori che conosco
			detectionExt.setId(detection.getId());
			detectionExt.setLicensePlate(detection.getLicensePlate());
			detectionExt.setSpeedValue(detection.getSpeedValue());
			detectionExt.setRegion(detection.getRegion());
			detectionExt.setRoadType(detection.getRoadType());
			detectionExt.setSpeedCameraId(detection.getSpeedCameraId());
			detectionExt.setOvercameLimit(overcameLimit);
			//cerco i valori che non conosco
			Optional<Car> optionalCar = carRepository.findById(detection.getLicensePlate());
			if(optionalCar.isPresent()) {
				Car car = optionalCar.get();
				detectionExt.setCarType(car.getCarType());
				detectionExt.setFuelType(car.getFuelType());
			}
			String json = gson.toJson(detectionExt);
			jmsTemplate.convertAndSend(statisticQueue, json);
			System.out.println("Sent message to Statistic");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
