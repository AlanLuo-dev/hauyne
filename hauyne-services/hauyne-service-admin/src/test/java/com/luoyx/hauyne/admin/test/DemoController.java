package com.luoyx.hauyne.admin.test;

import com.luoyx.hauyne.admin.sys.enums.AuthorityTypeEnum;
import com.luoyx.hauyne.admin.sys.service.DictTypeService;
import com.luoyx.hauyne.admin.sys.response.DictTypeDetailVO;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.commons.vo.BaseVO;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
@Validated
public class DemoController {

    private final DictTypeService dictTypeService;

    @Operation(summary = "/测试校验")
    @PostMapping(value = "/validate")
    public List<String> validate(@RequestBody(required = false)
                                 @NotNull(message = "不能为null")
                                 @NotEmpty(message = "不能为空数组")
                                 List<
                                         @NotNull(message = "不能包含NULL")
                                         @EnumCheck(enumClazz = AuthorityTypeEnum.class, getterMethod = "getValue")
                                                 String> authorities) {
        return authorities;
    }

    @Operation(summary = "测试AOP切点-[List<BaseVO>返回类型]")
    @GetMapping(value = "/testAopList")
    public List<BaseVO> testAopA() {
        return new ArrayList<>();
    }

    @Operation(summary = "测试AOP切点-[PageResult<BaseVO>返回类型]")
    @GetMapping(value = "/testAopPageResult")
    public PageResult<BaseVO> testAopB() {
        return new PageResult<>();
    }

    @Operation(summary = "测试AOP切点-[BaseVO返回类型]")
    @PostMapping(value = "/testAopBaseVO")
    public BaseVO testAopC() {
        return new BaseVO();
    }


    @Operation(summary = "测试AOP切点-[List<DictTypeDetailVO>返回类型]")
    @PostMapping(value = "/testAopDictTypeList")
    public List<DictTypeDetailVO> dictTypeList(){
        return dictTypeService.list().stream()
                .map(item -> {
                    DictTypeDetailVO detailVO = new DictTypeDetailVO();
                    detailVO.setId(item.getId());
//                    detailVO.setDictTypeCode();
                    detailVO.setDictTypeName(item.getDictTypeName());
//                    detailVO.setDescription();
//                    detailVO.setEnabled();
//                    detailVO.setId();
//                    detailVO.setCreatedBy();
//                    detailVO.setCreatedByFullName();
//                    detailVO.setCreatedTime();
//                    detailVO.setLastModifiedBy();
//                    detailVO.setLastModifiedByFullName();
//                    detailVO.setLastModifiedTime();
                    return detailVO;
                })
                .collect(Collectors.toList());
    }
}
