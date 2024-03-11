/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import ir.shenakht.paint.domain.Racing;
import java.io.IOException;

/**
 *
 * @author hossi
 */
public class RacingSerializer extends JsonSerializer<Racing> implements ContextualSerializer {

    public RacingSerializer() {
    }

    @Override
    public void serialize(Racing t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeObject(t);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider sp, BeanProperty bp) throws JsonMappingException {
        return new RacingSerializer();
    }

}
