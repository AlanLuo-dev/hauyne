package com.luoyx.hauyne.admin.sys.controller;


import com.luoyx.hauyne.admin.api.sys.LoginHistoryAPI;
import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import com.luoyx.hauyne.admin.sys.query.LoginHistoryQuery;
import com.luoyx.hauyne.admin.sys.response.LoginHistoryVO;
import com.luoyx.hauyne.admin.sys.service.LoginHistoryService;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录历史表 前端控制器
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
@Tag(name = "登录历史")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/login-histories")
public class LoginHistoryController implements LoginHistoryAPI {

    private final LoginHistoryService loginHistoryService;

    @GetMapping
    public PageResult<LoginHistoryVO> list(@Validated LoginHistoryQuery loginHistoryQuery) throws Exception {
        return loginHistoryService.findPage(loginHistoryQuery);
    }

    /**
     * 保存登录日志
     *
     * @param saveLoginHistoryDTO 登录日志请求参数
     */
    @Operation(summary = "保存登录日志")
    @PostMapping
    @Override
    public void save(@RequestBody SaveLoginHistoryDTO saveLoginHistoryDTO) {
        loginHistoryService.save(saveLoginHistoryDTO);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        int i = loginHistoryService.logicDeleteById(id);
        System.out.println(i);
    }
}

