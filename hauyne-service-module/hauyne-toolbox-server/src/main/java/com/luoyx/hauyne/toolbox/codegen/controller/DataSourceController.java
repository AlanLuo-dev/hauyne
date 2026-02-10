//package com.luoyx.hauyne.toolbox.codegen.controller;
//
//import com.luoyx.hauyne.jpa.dto.PageResult;
//import com.luoyx.hauyne.toolbox.codegen.converter.DataSourceConverter;
//import com.luoyx.hauyne.toolbox.codegen.entity.DataSource;
//import com.luoyx.hauyne.toolbox.codegen.query.DataSourceQuery;
//import com.luoyx.hauyne.toolbox.codegen.repository.DataSourceRepository;
//import com.luoyx.hauyne.toolbox.codegen.service.DataSourceService;
//import com.luoyx.hauyne.toolbox.codegen.vo.response.DataSourcePageResultVO;
//import io.swagger.annotations.Api;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * <p>
// * 数据源配置表 前端控制器
// * </p>
// *
// * @author 1564469545@qq.com
// * @since 2023-03-31
// */
//@Slf4j
//@Api(tags = "数据源配置")
//@RestController
//@RequestMapping("/codegen/data-sources")
//@RequiredArgsConstructor
//public class DataSourceController {
//
//    private final DataSourceService dataSourceService;
//    private final DataSourceConverter dataSourceConverter;
//    private final DataSourceRepository dataSourceRepository;
//
//    /**
//     * 查询数据源列表
//     *
//     * @return
//     */
//    @Operation(summary = "查询数据源列表")
//    @GetMapping
//    public PageResult<DataSourcePageResultVO> list(@Validated DataSourceQuery query) {
//        List<DataSource> all = dataSourceService.findAll();
//        return dataSourceService.pageList(query);
//    }
////
////    /**
////     * 查看数据源详情
////     *
////     * @param id 主键id
////     * @return
////     */
////    @Operation(summary = "查看数据源详情")
////    @GetMapping(value = "/{id}")
////    public DataSourceListVO detail(@Parameter(name = "主键id")
////                                   @NotNull(message = "id不能为空")
////                                   @PathVariable(value = "id") Long id) {
////        return dataSourceConverter.toDataSourceListVO(dataSourceService.getById(id));
////    }
////
////    /**
////     * 新增数据源配置
////     *
////     * @param dataSourceAddFormVO 新增数据源配置表单参数
////     */
////    @Operation(summary = "新增数据源配置")
////    @ResponseStatus(HttpStatus.CREATED)
////    @PostMapping
////    public DataSource add(@Validated @RequestBody DataSourceAddFormVO dataSourceAddFormVO) {
////        DataSource dataSource = dataSourceConverter.toDataSource(dataSourceAddFormVO);
////        dataSourceService.save(dataSource);
////
////        return dataSource;
////    }
////
////    /**
////     * 编辑数据源配置
////     *
////     * @param dataSourceEditFormVO 编辑数据源配置表单参数
////     */
////    @Operation(summary = "编辑数据源配置")
////    @PutMapping
////    public void modify(@Validated @RequestBody DataSourceEditFormVO dataSourceEditFormVO) {
////        dataSourceService.updateById(dataSourceConverter.toDataSource(dataSourceEditFormVO));
////    }
////
////    /**
////     * 删除数据源配置
////     *
////     * @param id
////     */
////    @Operation(summary = "删除数据源配置")
////    @ResponseStatus(HttpStatus.NO_CONTENT)
////    @DeleteMapping(value = "/{id}")
////    public void delete(@Parameter(name = "主键id")
////                       @NotNull(message = "id不能为空")
////                       @PathVariable(value = "id") Long id) {
////        if (null == dataSourceService.getById(id)) {
////            throw new DataNotFoundException("数据源配置不存在");
////        }
////        dataSourceService.removeById(id);
////    }
//
//
//}
//
