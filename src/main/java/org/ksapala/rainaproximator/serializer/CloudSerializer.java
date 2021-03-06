package org.ksapala.rainaproximator.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class CloudSerializer extends JsonSerializer<Cloud> {

    @Autowired
    private Configuration configuration;

    @Override
    public void serialize(Cloud cloud, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("line", cloud.getLineAsString());
        jsonGenerator.writeStringField("time", TimeUtils.timeToString(cloud.getTime(), configuration));
        jsonGenerator.writeEndObject();
    }
}
