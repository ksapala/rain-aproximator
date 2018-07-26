package org.ksapala.rainaproximator.rest.bean;

import org.ksapala.rainaproximator.util.Utils;

import java.util.Date;


public class AproximationResultBean {

    public String info;
    public String date;
    public String remark;

    public AproximationResultBean(String info, Date date, String remark) {
        this.info = info;
        if (date != null) {
            this.date = Utils.format(date);
        }
        this.remark = remark;
    }

    public static final AproximationResultBean HELLO = new AproximationResultBean("Hello, this is example result!",
            new Date(), "No remark.");

}
