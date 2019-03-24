package org.ksapala.rainaproximator.aproximation.debug;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.serializer.CloudDebugSerializer;

@Getter
@JsonSerialize(using = CloudDebugSerializer.class)
public class CloudDebug {

    private String info;
    private Cloud cloud;

    public CloudDebug(Cloud cloud) {
        this.cloud = cloud;
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
