package com.luoyx.hauyne.swaggerv3_enum;

import com.luoyx.hauyne.swaggerv3_enum.enums.FormDTO;
import com.luoyx.hauyne.swaggerv3_enum.request.EnumInQuery;
import com.luoyx.hauyne.swaggerv3_enum.response.EnumInResult;
import com.luoyx.hauyne.swaggerv3_enum.response.FormResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/EnumController")
public class TestController {


    @Operation(summary = "测试普通表单")
    @PostMapping(value = "/testFormDTO", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FormResult testFormDTO(@Parameter FormDTO formDTO) {
        FormResult  formResult = new FormResult();
        formResult.setPassword(formDTO.getPassword());
        formResult.setUsername(formDTO.getUsername());
        formResult.setTestCodeEnum(formDTO.getTestCodeEnum());
        return formResult;
    }

    @Operation(summary = "测试Json枚举接收参数")
    @PostMapping(value = "/testJsonEnum")
    public EnumInResult testJsonEnum(@RequestBody EnumInQuery jsonQuery) {
        EnumInResult enumInResult = new EnumInResult();
        enumInResult.setColor(jsonQuery.getColor());
        return enumInResult;
    }
}
