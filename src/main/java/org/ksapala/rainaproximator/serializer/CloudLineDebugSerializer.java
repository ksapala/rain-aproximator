package org.ksapala.rainaproximator.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ksapala.rainaproximator.aproximation.debug.CloudLineDebug;

import java.io.IOException;

public class CloudLineDebugSerializer extends StdSerializer<CloudLineDebug> {

    public CloudLineDebugSerializer() {
        this(null);
    }

    public CloudLineDebugSerializer(Class<CloudLineDebug> t) {
        super(t);
    }

    @Override
    public void serialize(
            CloudLineDebug value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("i", value.getInfo());
        jgen.writeStringField("line", value.getCloudLine().getLineAsString());
        jgen.writeStringField("time", value.getCloudLine().getTime().toString());
        jgen.writeEndObject();
    }
}
