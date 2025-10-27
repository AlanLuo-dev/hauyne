//package com.luoyx.hauyne.admin.util;
//
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Mybatis plus 代码生成工具类
// *
// * @author 罗英雄
// */
//public class MpGenerator {
//    /**
//     * <p>
//     * MySQL 生成演示
//     * </p>
//     */
//    public static void main(String[] args) {
//        AutoGenerator mpg = new AutoGenerator();
//        // 选择 freemarker 引擎，默认 Veloctiy
//        // mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
////        String outputDir = System.getProperty("user.dir") + "\\" ;
//
//        // 代码输出目录 按操作系统指定不同的目录
//        String os = System.getProperty("os.name");
//        if (os.toLowerCase().contains("linux")) {
//            gc.setOutputDir("/home/luoyingxiong/myOutput");
//        } else {
//            gc.setOutputDir("D:\\myOutput");
//        }
//
//
//
//
//        gc.setFileOverride(true);
//        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
//        gc.setEnableCache(false);// XML 二级缓存
//        gc.setBaseResultMap(true);// XML ResultMap
//        gc.setBaseColumnList(false);// XML columList
//        // .setKotlin(true) 是否生成 kotlin 代码
//        gc.setAuthor("1564469545@qq.com");
//
//        // 自定义文件命名，注意 %s 会自动填充表实体属性！
//        gc.setMapperName("%sMapper");
//        gc.setXmlName("%sMapper");
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sServiceImpl");
//        gc.setControllerName("%sController");
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setDbType(DbType.MYSQL);
////        dsc.setTypeConvert(new MySqlTypeConvert() {
////            // 自定义数据库表字段类型转换【可选】
////            @Override
////            public DbColumnType processTypeConvert(String fieldType) {
////                System.out.println("转换类型：" + fieldType);
////                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
////                return super.processTypeConvert(fieldType);
////            }
////        });
////        dsc.setDriverName("com.mysql.jdbc.Driver");
////        dsc.setUsername("admin");
////        dsc.setPassword("a123456");
////        dsc.setUrl("jdbc:mysql://192.168.0.4:3306/wherebuy?useUnicode=true&characterEncoding=utf-8&autoReconnect=true");
//
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("avnadmin");
//        dsc.setUrl("jdbc:mysql://hauyne-dev-luoyingxiong123-9aa4.c.aivencloud.com:16704/hauyne_tracelog_dev?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&allowMultiQueries=true");
//
//        mpg.setDataSource(dsc);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[]{"hyn_trace_"});// 此处可以修改为您的表前缀
//        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
//        strategy.setInclude(new String[]{"hyn_trace_user_profile"}); // 需要生成的表
//        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
//
//        strategy.setEntitySerialVersionUID(false);
//
//        strategy.setEntityLombokModel(true);
//
//        // 自定义实体父类
//         strategy.setSuperEntityClass("com.luoyx.hauyne.mybatisplus.entity.BaseEntity");
//        // 自定义实体，公共字段
//         strategy.setSuperEntityColumns(new String[] { "id", "created_by","created_time","last_updated_by","last_updated_time" });
//
//        // 自定义 mapper 父类
//        strategy.setSuperMapperClass("com.luoyx.hauyne.mybatisplus.mapper.GenericMapper");
//        // 自定义 service 父类
//        strategy.setSuperServiceClass("com.luoyx.hauyne.mybatisplus.service.BaseService");
//        // 自定义 service 实现类父类
//        strategy.setSuperServiceImplClass("com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl");
//        // 自定义 rest 父类
////        strategy.setSuperControllerClass("com.luoyx.hauyne.base.controller.BaseController");
//        // 【实体】是否生成字段常量（默认 false）
//        // public static final String ID = "test_id";
//        // strategy.setEntityColumnConstant(true);
//        // 【实体】是否为构建者模型（默认 false）
//        // public User setName(String name) {this.name = name; return this;}
//        // strategy.setEntityBuilderModel(true);
//        mpg.setStrategy(strategy);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent("com.luoyx.hauyne.tracelog");
////        pc.setModuleName("tracelog");//模块报名
//        pc.setEntity("entity");
//        mpg.setPackageInfo(pc);
//
//        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };
//
//        // 自定义 xxList.jsp 生成
//        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
////        focList.add(new FileOutConfig("/template/list.jsp.vm") {
////            @Override
////            public String outputFile(TableInfo tableInfo) {
////                // 自定义输入文件名称
////                return "D://my_" + tableInfo.getEntityName() + ".jsp";
////            }
////        });
////        cfg.setFileOutConfigList(focList);
////        mpg.setCfg(cfg);
//
//        // 调整 xml 生成目录演示
//        /*focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
//            }
//        });
//        cfg.setFileOutConfigList(focList);*/
//        mpg.setCfg(cfg);
//
//        // 关闭默认 xml 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
////        tc.setXml(null);
//        mpg.setTemplate(tc);
//
//        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
//        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
//        // TemplateConfig tc = new TemplateConfig();
//        // tc.setController("...");
//        // tc.setEntity("...");
//        // tc.setMapper("...");
//        // tc.setXml("...");
//        // tc.setService("...");
//        // tc.setServiceImpl("...");
//        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
//        // mpg.setTemplate(tc);
//
//        // 执行生成
//        mpg.execute();
//
//        // 打印注入设置【可无】
//        System.err.println(mpg.getCfg().getMap().get("abc"));
//    }
//}
