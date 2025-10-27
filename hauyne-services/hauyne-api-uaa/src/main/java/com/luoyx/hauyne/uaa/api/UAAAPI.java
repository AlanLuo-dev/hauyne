package com.luoyx.hauyne.uaa.api;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public interface UAAAPI {

    @GetMapping(value = "/test-call-uaa")
    Map<String, String> testCallUAA();

}
