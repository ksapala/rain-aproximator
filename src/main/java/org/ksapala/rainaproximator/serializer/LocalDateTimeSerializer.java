package org.ksapala.rainaproximator.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author krzysztof
 */
@JsonComponent
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Autowired
    private Configuration configuration;

    @Override
    public void serialize(LocalDateTime time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(TimeUtils.timeToString(time, configuration));

    }
}
