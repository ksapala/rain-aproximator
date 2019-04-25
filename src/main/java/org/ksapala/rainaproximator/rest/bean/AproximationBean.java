package org.ksapala.rainaproximator.rest.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.aproximation.result.Accuracy;
import org.ksapala.rainaproximator.serializer.CloudDebugSerializer;
import org.ksapala.rainaproximator.serializer.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AproximationBean {


    public static final AproximationBean HELLO = new AproximationBean("Hello, this is example result!",
            "", null, "No remark.", true, null, null);

    private String info;
    private String day;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;

    private String remark;
    private boolean notificationSuggested;
    private Accuracy accuracy;
    private Debug debug;


}
