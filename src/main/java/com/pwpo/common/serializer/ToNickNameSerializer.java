package com.pwpo.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pwpo.user.UserAccount;

import java.io.IOException;

public class ToNickNameSerializer extends JsonSerializer<UserAccount> {

    @Override
    public void serialize(UserAccount userAccount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(userAccount.getNick());
    }
}
