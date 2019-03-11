package org.ksapala.rainaproximator.aproximation.debug;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.serializer.CloudLineDebugSerializer;

import java.time.LocalDateTime;

@Getter
@JsonSerialize(using = CloudLineDebugSerializer.class)
public class CloudLineDebug {

    private String info;
    private CloudLine cloudLine;

    public CloudLineDebug(CloudLine cloudLine) {
        this.cloudLine = cloudLine;
        this.info = "";
    }

    public void appendInfo(String info) {
        if (this.info.isEmpty()) {
            this.info = info;
        } else {
            this.info += "." + info;
        }
    }
}
