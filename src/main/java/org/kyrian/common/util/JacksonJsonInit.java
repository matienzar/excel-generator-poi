package org.kyrian.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

/**
 * Jackson Initialization
 * {@link -linkoffline https://wiki.fasterxml.com/JacksonFAQ}
 * {@link -linkoffline https://wiki.fasterxml.com/JacksonFAQThreadSafety}
 *
 * @author matienzar
 * @see ObjectMapper
 */
enum JacksonJsonInit {
    INSTANCE;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    public ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

}
