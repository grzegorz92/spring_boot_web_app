package com.testprojects.firstapp.model;

import org.springframework.stereotype.Component;

@Component
public class Props {

    private String key;
    private String value;

    public Props() {
    }

    public Props(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


