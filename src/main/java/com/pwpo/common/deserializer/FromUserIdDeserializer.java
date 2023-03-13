package com.pwpo.common.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pwpo.user.UserAccount;

import java.io.IOException;

public class FromUserIdDeserializer extends JsonDeserializer<UserAccount> {

    @Override
    public UserAccount deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        long id = jsonParser.getValueAsLong();

        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);

        return userAccount;
    }
}
