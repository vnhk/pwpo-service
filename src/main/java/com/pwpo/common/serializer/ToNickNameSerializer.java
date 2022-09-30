package com.pwpo.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pwpo.user.UserDetails;

import java.io.IOException;

public class ToNickNameSerializer extends JsonSerializer<UserDetails> {

    @Override
    public void serialize(UserDetails userDetails, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(userDetails.getNick());
    }
}
