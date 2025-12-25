package com.luoyx.hauyne.admin.sys.controller;

import com.luoyx.hauyne.admin.sys.enums.TestCodeEnum;
import com.luoyx.hauyne.admin.sys.query.EnumInQuery;
import com.luoyx.hauyne.admin.sys.query.JsonQuery;
import com.luoyx.hauyne.admin.sys.request.FormDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/EnumController")
public class TestEnumController {

    @Operation(summary = "测试枚举接收参数")
    @GetMapping(value = "/testEnum")
    public TestCodeEnum test(@Parameter(description = "测试Query枚举参数") @RequestParam TestCodeEnum testCodeEnum) {
        return testCodeEnum;
    }

    @Operation(summary = "测试Json枚举接收参数")
    @PostMapping(value = "/testJsonEnum")
    public JsonQuery testJsonEnum(@RequestBody JsonQuery jsonQuery) {
        return jsonQuery;
    }

    @Operation(summary = "测试普通表单")
    @PostMapping(value = "/testFormDTO", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FormDTO testFormDTO(@Parameter FormDTO formDTO) {
        return formDTO;
    }

    @Operation(summary = "测试枚举在Query类中")
    @GetMapping(value = "/testBoolEnum")
    public EnumInQuery test2(@ParameterObject EnumInQuery enumInQuery) {
        return enumInQuery;
    }
}
