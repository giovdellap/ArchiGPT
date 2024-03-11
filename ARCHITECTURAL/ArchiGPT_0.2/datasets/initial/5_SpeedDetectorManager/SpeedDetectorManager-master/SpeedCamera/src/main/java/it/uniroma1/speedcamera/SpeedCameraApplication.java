package it.uniroma1.speedcamera;

import com.google.gson.Gson;
import it.uniroma1.commons.queue.enums.RoadType;
import it.uniroma1.commons.queue.object.Detection;
import it.uniroma1.commons.utility.ParamConnection;
import it.uniroma1.speedcamera.producer.Producer;
import javax.jms.JMSException;
import java.util.Random;

public class SpeedCameraApplication {
	private static final String[] plates = new String[]{"GB786KM", "FC679LM", "GK986KM"};

	private final static RoadType roadType  = RoadType.getRandomRoadType();
	public static void main(String[] args) throws JMSException {
		long i = 0;
		while(true) {
			try {
				Thread.sleep(8000);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			Detection detection = new Detection();

			detection.setId(i);
			detection.setSpeedCameraId(Long.parseLong(args[0]));
			detection.setSpeedValue(generateRandomSpeed());
			detection.setLicensePlate(generateRandomPlate());
			detection.setRoadType(roadType);

			Gson gson = new Gson();

			String json = gson.toJson(detection);
			sendMsg(json);
			i++;
		}

	}
	
	private static void sendMsg(String message) {
		Producer producter = new Producer(ParamConnection.BROKER_URL, ParamConnection.DETECTION_QUEUE);
		producter.sendMessage(message);
	}

//	private static String generateRandomLicensePlate() {
//		int alpha1 = 'A' + (int)(Math.random() * ('Z' - 'A'));
//		int alpha2 = 'A' + (int)(Math.random() * ('Z' - 'A'));
//		int alpha3 = 'A' + (int)(Math.random() * ('Z' - 'A'));
//		int alpha4 = 'A' + (int)(Math.random() * ('Z' - 'A'));
//		int digit1 = (int)(Math.random() * 10);
//		int digit2 = (int)(Math.random() * 10);
//		int digit3 = (int)(Math.random() * 10);
//
//		return "" + (char)(alpha1) + ((char)(alpha2)) +
//				digit1 + digit2 + digit3 + ((char)(alpha3)) + ((char)(alpha4));
//	}

	private static String generateRandomPlate() {
		Random rand = new Random();
		return plates[rand.nextInt(plates.length)];
	}

	private static int generateRandomSpeed() {
		Random random = new Random();
		return random.nextInt(200 - 80) + 80;
	}

}
