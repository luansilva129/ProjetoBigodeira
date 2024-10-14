package br.com.atlas.bigodeira.backend.controller;


import jakarta.annotation.PostConstruct;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashMap;
import java.util.Map;


public abstract class ControllerBase {

    @Getter
    Map<String, Object> viewParams = new HashMap<>();

    public void setViewAttributes(Map<String, Object> attributes) {
        viewParams.putAll(attributes);
    }

    @PostConstruct
    public void postConstruct() throws Exception {
    }
}


