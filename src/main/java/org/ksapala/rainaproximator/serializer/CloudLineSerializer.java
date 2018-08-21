package org.ksapala.rainaproximator.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;

import java.io.IOException;

public class CloudLineSerializer extends StdSerializer<CloudLine> {

    public CloudLineSerializer() {
        this(null);
    }

    public CloudLineSerializer(Class<CloudLine> t) {
        super(t);
    }

    @Override
    public void serialize(
            CloudLine value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("line", value.getLineAsString());
        jgen.writeStringField("time", value.getTime().toString());
        jgen.writeEndObject();
    }
}
