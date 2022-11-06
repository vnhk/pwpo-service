package com.pwpo.common.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pwpo.project.model.Project;

import java.io.IOException;

public class FromProjectIdDeserializer extends JsonDeserializer<Project> {

    @Override
    public Project deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        long id = jsonParser.getValueAsLong();

        Project project = new Project();
        project.setId(id);

        return project;
    }
}
