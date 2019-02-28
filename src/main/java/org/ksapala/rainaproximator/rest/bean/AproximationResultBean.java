package org.ksapala.rainaproximator.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.aproximation.result.Accuracy;

@AllArgsConstructor
@Getter
public class AproximationResultBean {


    public static final AproximationResultBean HELLO = new AproximationResultBean("Hello, this is example result!",
            "", "No remark.", null, null);

    private String info;
    private String time;
    private String remark;
    private Accuracy accuracy;
    private Debug debug;

}
