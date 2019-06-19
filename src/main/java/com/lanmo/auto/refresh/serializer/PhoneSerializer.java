package com.lanmo.auto.refresh.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lanmo.auto.refresh.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class PhoneSerializer extends JsonSerializer<String> {


    @Autowired
    private UserService userService;


    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(userService.hidePhone()){
            s = s.substring(0, 3) + "****" + s.substring(7, s.length());
        }
        jsonGenerator.writeString(s);
    }
}
