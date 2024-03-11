package it.uniroma1.commons.queue.handler;

import com.google.gson.Gson;
import it.uniroma1.commons.queue.producer.Producer;
import it.uniroma1.commons.utility.ParamConnection;

public class MessageHandler {
    private Gson gson = new Gson();
    public Object handleMessage(String message, Class cls) {
        Object obj = gson.fromJson(message, cls);
        return obj;
    }

    public void sendObject(Object message, String queue) {
        Producer producter = new Producer(ParamConnection.BROKER_URL, queue);
        String text = gson.toJson(message);
        producter.sendMessage(text);
    }
}
