package com.shopping.integral.base;

import lombok.Data;


@Data
public class LogEntity {
    private String appkey;

    private String name;

    private String info;

    @Override
    public String toString() {
        return "LogEntity{" +
                "appkey='" + appkey + '\'' +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
