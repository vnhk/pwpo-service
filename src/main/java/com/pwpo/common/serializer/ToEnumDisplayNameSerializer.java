package com.pwpo.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pwpo.common.enums.DataEnum;

import java.io.IOException;

public class ToEnumDisplayNameSerializer extends JsonSerializer<DataEnum> {
    @Override
    public void serialize(DataEnum dataEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(dataEnum.getDisplayName());
    }
}
