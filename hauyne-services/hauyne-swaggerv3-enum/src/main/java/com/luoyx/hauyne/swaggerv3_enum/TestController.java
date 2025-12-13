package com.luoyx.hauyne.swaggerv3_enum;

import com.luoyx.hauyne.swaggerv3_enum.enums.FormDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/EnumController")
public class TestController {


    @Operation(summary = "测试普通表单")
    @PostMapping(value = "/testFormDTO", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FormDTO testFormDTO(@Parameter FormDTO formDTO) {
        return formDTO;
    }

}
