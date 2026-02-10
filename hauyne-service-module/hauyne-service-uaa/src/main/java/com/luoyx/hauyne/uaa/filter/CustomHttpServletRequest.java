package com.luoyx.hauyne.uaa.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * 增加请求参数
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String[]> additionalParams;
    private final HttpServletRequest request;

    public CustomHttpServletRequest(final HttpServletRequest request, final Map<String, String[]> additionalParams) {
        super(request);
        this.request = request;
        this.additionalParams = additionalParams;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        final Map<String, String[]> map = request.getParameterMap();
        final Map<String, String[]> param = new HashMap<String, String[]>();
        param.putAll(map);
        param.putAll(additionalParams);

        return param;
    }
}