package com.pronoia.karaf.service.impl;

import com.pronoia.karaf.service.Hello;

public class TestHelloService implements Hello {
    @Override
    public String hello() {
        return "Hello from test service";
    }
}
