package com.omega.cowalk.domain.entity.backgroundsound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.List;

public class SteppingSoundConverter implements AttributeConverter<List<SteppingSound>, String>
{
    private final static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @Override
    public String convertToDatabaseColumn(List<SteppingSound> steppingSounds) {
        try {
            if(steppingSounds == null)
            {
                return null;
            }
            return objectMapper.writeValueAsString(steppingSounds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<SteppingSound> convertToEntityAttribute(String dbData) {
        try {
            if(dbData == null)
            {
                return null;
            }

            List<SteppingSound> steppingSounds =  objectMapper.readValue(dbData, List.class);
            return steppingSounds;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
