package com.luoyx.hauyne.admin.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class TestNumber {

    @GetMapping(value = "/jingdu")
    public Map<String, BigInteger> jingdu() {
        Map<String, BigInteger> map = new LinkedHashMap<>();

        BigInteger max = new BigInteger("18446744073709551615");
        log.info("max => {}", max);
        map.put("id", max);

        return map;

    }
}
