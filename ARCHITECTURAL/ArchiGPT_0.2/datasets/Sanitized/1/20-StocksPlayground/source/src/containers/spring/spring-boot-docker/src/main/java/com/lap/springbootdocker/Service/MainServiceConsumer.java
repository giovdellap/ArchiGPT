package com.lap.springbootdocker.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lap.springbootdocker.Configuration.*;
import com.lap.springbootdocker.Model.DataInfo;
import com.lap.springbootdocker.Repository.DataRepository;

import java.util.Date;  
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class MainServiceConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    DataRepository dataRepository;

    private Integer corrId;

    public MainServiceConsumer() {
        corrId = 0;
    }

    private String checkDatabase(String message){
        String response = null;
        if(dataRepository.existsByType(message)){
            DataInfo doc = dataRepository.findDataByType(message);
            Date d = doc.getDate();
            response = doc.getData();

            // sono passati 2 minuti oppure è stato salvato erroneamente ''
            if (new Date().getTime() - d.getTime() > 120000 || response.length()==0){ 
                dataRepository.deleteByType(message);
                return null;
            }
        }
        return response;
    }

    private void setDatabase(String type, String jsonData){
        if (!dataRepository.existsByType(type)) {
            Date date = new Date();
            DataInfo d = new DataInfo(type, date, jsonData);
            dataRepository.save(d);
        }
    }

    private String performMessage(String message, HashMap<String, String> params) throws Exception{
        corrId = corrId + 1;

        MessageProperties properties = new MessageProperties();
        properties.setCorrelationId(corrId.toString());

        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode rootNode = jsonMapper.createObjectNode();
        rootNode.put("type", message);
        rootNode.put("id", corrId.toString());
        
        for (String i : params.keySet()) {
            rootNode.put(i, params.get(i));
        }

        String jsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        properties.setMessageId(jsonString);

        // Create a message subject
        Message newMessage = new Message(message.getBytes(), properties);

        System.out.println(newMessage);

        // The customer sends a message
        Message result = rabbitTemplate.sendAndReceive(RabbitConfiguration.RPC_MESSAGE_QUEUE, newMessage);

        System.out.println(result);
        String response = "";
        if (result != null) {
            // To get message sent correlationId
            JSONObject json = new JSONObject(newMessage.getMessageProperties().getMessageId());  
            String correlationId = json.getString("id");  

            // Access server Message returned id
            String msgId = result.getMessageProperties().getMessageId();

            if (msgId.equals(correlationId)) {
                response = new String(result.getBody());
            }
        }
        return response;
    }

    public String GetData() throws Exception {
        String message = "DATI";
        String response = checkDatabase(message);
        
        if (response != null){
            return response;
        }

        HashMap<String,String> params = new HashMap<String,String >();
        response = performMessage(message, params);
        setDatabase(message, response);
        return response;
    }

    public String GetCryptoData() throws Exception {
        String message = "CRYPTO";
        String response = checkDatabase(message);
        
        if (response != null){
            return response;
        }

        HashMap<String,String> params = new HashMap<String,String >();
        response = performMessage(message, params);
        setDatabase(message, response);
        return response;
    }

    public String GetSearchResult(String q) throws Exception {
        String message = "SEARCH";
        
        HashMap<String,String> params = new HashMap<String,String >();

        JSONObject json = new JSONObject(q);  
        String search = json.getString("q"); 
        params.put("q", search);
        
        String response = performMessage(message, params);
        return response;
    }

    public String GetNews() throws Exception {
        String message = "NEWS";
        String response = checkDatabase(message);
        
        if (response != null){
            return response;
        }

        HashMap<String,String> params = new HashMap<String,String >();
        response = performMessage(message, params);
        setDatabase(message, response);
        return response;
    }

    public String GetBoxes() throws Exception {
        String message = "BOX";
        String response = checkDatabase(message);
        
        if (response != null){
            return response;
        }

        HashMap<String,String> params = new HashMap<String,String >();
        response = performMessage(message, params);
        setDatabase(message, response);
        return response;
    }

    public String GetGraph(String symbol) throws Exception {
        String message = "GRF";
        HashMap<String,String> params = new HashMap<String,String >();
        params.put("ticker", symbol);

        String response = performMessage(message, params);
        return response;
    }

    public String GetStockData(String symbol) throws Exception {
        String message = "STCK";
        HashMap<String,String> params = new HashMap<String,String >();
        params.put("ticker", symbol);

        String response = performMessage(message, params);
        return response;
    }
}
