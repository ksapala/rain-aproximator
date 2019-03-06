package org.ksapala.rainaproximator.aproximation.debug;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.serializer.CloudLineDebugSerializer;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@JsonSerialize(using = CloudLineDebugSerializer.class)
public class CloudLineDebug {

    private String i;
    private String line;
    private LocalDateTime time;

}
