package org.ksapala.rainaproximator.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ksapala.rainaproximator.aproximation.debug.CloudDebug;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class CloudDebugSerializer extends JsonSerializer<CloudDebug> {

    @Autowired
    private Configuration configuration;

    @Override
    public void serialize(CloudDebug cloudDebug, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("i", cloudDebug.getInfo());
        jsonGenerator.writeStringField("line", cloudDebug.getCloud().getLineAsString());
        jsonGenerator.writeStringField("time", TimeUtils.timeToString(cloudDebug.getCloud().getTime(), configuration));
        jsonGenerator.writeEndObject();
    }
}
