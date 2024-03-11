package it.uniroma1.finesmanagement.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import it.uniroma1.commons.entity.Car;
import it.uniroma1.commons.entity.Fine;
import it.uniroma1.commons.entity.Person;
import it.uniroma1.commons.entity.SpeedCamera;
import it.uniroma1.commons.queue.enums.RoadType;
import it.uniroma1.commons.queue.handler.MessageHandler;
import it.uniroma1.commons.queue.object.Detection;
import it.uniroma1.commons.queue.object.DetectionExt;
import it.uniroma1.commons.repository.CarRepository;
import it.uniroma1.commons.repository.FineRepository;
import it.uniroma1.commons.repository.PersonRepository;
import it.uniroma1.commons.repository.SpeedCameraRepository;
import it.uniroma1.commons.utility.ParamConnection;
import it.uniroma1.commons.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.net.ssl.SSLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ConsumerSpringFM {
	@Autowired
	@Qualifier("statistic_queue")
	private Queue adminQueue;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	FineRepository fineRepository;

	@Autowired
	CarRepository carRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	SpeedCameraRepository speedCameraRepository;

	MessageHandler messageHandler = new MessageHandler();
	private static final Logger logger = LoggerFactory.getLogger(ConsumerSpringFM.class);

	@JmsListener(destination = ParamConnection.SPEED_DETECTION_QUEUE)
	public void consumeMessage(String message) {
		logger.info("Message received from activemq queue---"+message);

		Detection detection = (Detection) messageHandler.handleMessage(message, Detection.class);
		Fine fine =  new Fine();
		//search SpeedCamera on DB
		Optional<SpeedCamera> optionalSpeedCamera = speedCameraRepository.findById(detection.getSpeedCameraId());
		if(optionalSpeedCamera.isEmpty()) {
			logger.info("SpeedCamera is not present, we cannot create fine");
			return;
		}
		fine.setSpeedCamera(optionalSpeedCamera.get());
		//settare il manager
		//cerco se la macchina è registrata
		Optional<Car> optionalCar = carRepository.findById(detection.getLicensePlate());
		Car car = optionalCar.isPresent()?optionalCar.get():null;
		if(optionalCar.isEmpty()) {
			logger.info("license plate is not present, we cannot retrieve car info");
		}
		if(car != null) fine.setCar(car);
		//inserisco la persona proprietaria della macchina
		if(car!=null && car.getOwner()!=null)fine.setReceiver(car.getOwner());
		fine.setDate(LocalDateTime.now());
		fine.setPoints(getFinePoint(detection));
		fine.setAmount(getFineAmount(getFinePoint(detection)));

		fineRepository.save(fine);
	}
	public static int getFinePoint(Detection detection) {
		int treshold = 0;
		switch (detection.getRoadType()) {
			case HIGHWAY: treshold = RoadType.HIGHWAY.getThreshold();
			 break;
			case THROUGHWAY: treshold = RoadType.THROUGHWAY.getThreshold();
				break;
			case CITY_ROAD: treshold = RoadType.CITY_ROAD.getThreshold();
				break;
			default:
				break;
		}
		int value = detection.getSpeedValue()-treshold;
		if(value > 0 && value <= 10) {
			return 1;
		} else if (value > 10 && value <= 20) {
			return 2;
		} else if (value > 20 && value <= 30) {
			return 5;
		} else if (value > 30 && value <= 40) {
			return 10;
		} else if (value > 40) {
			return 30;
		}
		return 0;
	}

	public static int getFineAmount(int point) {
		return point * 10;
	}
}

