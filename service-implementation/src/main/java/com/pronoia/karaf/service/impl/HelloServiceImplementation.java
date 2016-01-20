package com.pronoia.karaf.service.impl;

import com.pronoia.karaf.service.Hello;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloServiceImplementation implements Hello {

    private String say = "Hello World";

    public String hello() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "Hello Service Implementation " + this.hashCode() + ": " + say + " at " + sdf.format(new Date());
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }
}
