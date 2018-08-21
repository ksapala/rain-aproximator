package org.ksapala.rainaproximator.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.utils.Debug;

@AllArgsConstructor
@Getter
public class AproximationResultBean {


    public static final AproximationResultBean HELLO = new AproximationResultBean("Hello, this is example result!",
            "", "No remark.", null);

    private String info;
    private String time;
    private String remark;
    private Debug debug;

}
