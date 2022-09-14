package com.pwpo.common.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pwpo.user.UserDetails;

import java.io.IOException;

public class FromUserIdDeserializer extends JsonDeserializer<UserDetails> {

    @Override
    public UserDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        long id = jsonParser.getValueAsLong();

        UserDetails userDetails = new UserDetails();
        userDetails.setId(id);

        return userDetails;
    }
}
