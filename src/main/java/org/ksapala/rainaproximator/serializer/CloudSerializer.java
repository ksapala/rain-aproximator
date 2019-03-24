package org.ksapala.rainaproximator.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;

import java.io.IOException;

public class CloudSerializer extends StdSerializer<Cloud> {

    public CloudSerializer() {
        this(null);
    }

    public CloudSerializer(Class<Cloud> t) {
        super(t);
    }

    @Override
    public void serialize(
            Cloud value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("line", value.getLineAsString());
        jgen.writeStringField("time", value.getTime().toString());
        jgen.writeEndObject();
    }
}
