package org.ksapala.rainaproximator.rest.bean;

import lombok.AllArgsConstructor;
import org.ksapala.rainaproximator.utils.Debug;

@AllArgsConstructor
public class AproximationResultBean {


    public static final AproximationResultBean HELLO = new AproximationResultBean("Hello, this is example result!",
            "", "No remark.", "");

    public String info;
    public String time;
    public String remark;
    public String debug;

}
