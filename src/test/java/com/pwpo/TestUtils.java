package com.pwpo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.model.SortDirection;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestUtils {
    public static ObjectMapper getObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(module)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public static String buildParams(int page, int pageSize,
                                     String sortField, SortDirection sortDirection,
                                     String entityToFind, String query) {
        return String.format("?page=%d&pageSize=%d&sortField=%s&sortDirection=%s&entityToFind=%s&query=%s",
                page, pageSize, sortField, sortDirection.toString(), entityToFind, query);
    }

    public static <T> APIResponse<T> convertAPIResponse(MockHttpServletResponse response, Class<T> itemClass,
                                                        ObjectMapper mapper) throws Exception {
        APIResponse<T> apiResponse = mapper.readValue(response.getContentAsString(), APIResponse.class);

        List<T> items = apiResponse.getItems();

        List<T> newItems = new ArrayList<>();
        for (Object item : items) {
            String itemAsString = mapper.writeValueAsString(item);
            newItems.add(mapper.readValue(itemAsString, itemClass));
        }

        apiResponse.setItems(newItems);

        return apiResponse;
    }

    public static <T> T convertResponse(MockHttpServletResponse response, Class<T> itemClass, ObjectMapper mapper) throws Exception {
        return mapper.readValue(response.getContentAsString(), itemClass);
    }

    public static <S, T extends Collection<S>> T convertResponse(MockHttpServletResponse response, Class<T> collectionClass, ObjectMapper mapper, Class<S> elClass) throws Exception {
        T collection = mapper.readValue(response.getContentAsString(), collectionClass);
        T newCollection = (T) collection.getClass().getDeclaredConstructor().newInstance();

        for (S e : collection) {
            String content = mapper.writeValueAsString(e);
            newCollection.add(mapper.readValue(content, elClass));
        }

        return newCollection;
    }

    public static RequestBuilder buildPostRequest(String url, ItemDTO item, ObjectMapper mapper) throws JsonProcessingException {
        return MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(item));
    }

    public static RequestBuilder buildPutRequest(String url, ItemDTO item, ObjectMapper mapper) throws JsonProcessingException {
        return MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(item));
    }
}
