package com.zxf.hazelcast.springsession.bean;

import java.io.Serializable;

public class MyBean implements Serializable {
    private String foo;
    private String bar;

    public MyBean() {

    }

    public MyBean(String foo, String bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "{foo:" + this.foo + ",bar:" + this.bar + "}";
    }
}
