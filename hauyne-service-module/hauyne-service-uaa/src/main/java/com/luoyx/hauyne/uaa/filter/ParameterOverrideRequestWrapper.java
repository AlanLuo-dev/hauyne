package com.luoyx.hauyne.uaa.filter;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ParameterOverrideRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> params = new HashMap<>();

    public ParameterOverrideRequestWrapper(HttpServletRequest request, Map<String, String[]> additionalParams) {
        super(request);
        // 先把原来的参数放进来
        params.putAll(request.getParameterMap());
        // 覆盖/添加新的参数
        params.putAll(additionalParams);
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }
}
