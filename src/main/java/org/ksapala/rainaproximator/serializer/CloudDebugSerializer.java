package org.ksapala.rainaproximator.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ksapala.rainaproximator.aproximation.debug.CloudDebug;

import java.io.IOException;

public class CloudDebugSerializer extends StdSerializer<CloudDebug> {

    public CloudDebugSerializer() {
        this(null);
    }

    public CloudDebugSerializer(Class<CloudDebug> t) {
        super(t);
    }

    @Override
    public void serialize(
            CloudDebug value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("i", value.getInfo());
        jgen.writeStringField("line", value.getCloud().getLineAsString());
        jgen.writeStringField("time", value.getCloud().getTime().toString());
        jgen.writeEndObject();
    }
}
