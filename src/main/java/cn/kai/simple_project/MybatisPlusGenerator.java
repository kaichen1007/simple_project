package cn.kai.simple_project;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

public class MybatisPlusGenerator {

    public static void main(String[] args) {
        //数据库地址
        String url = "jdbc:mysql://127.0.0.1:3306/simple_project?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=GMT%2B8";
        //数据库用户名
        String username = "root";
        //数据库密码
        String password = "12345678";
        //生成类作者
        String author = "kaiChen";
        //项目模块
        String module = "simple_project";
        //生成类父级包名
        String packageName = "cn.kai";
        //生成类包名
        String moduleName = "rc";
        //需要生成的表名
        String[] tables = {"sys_user"};

        //1、配置数据源
        FastAutoGenerator.create(url, username, password)
                //2、全局配置
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者名
                            .outputDir(System.getProperty("user.dir") + "/"+ module+ "/src/main/java")   //设置输出路径
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .dateType(DateType.ONLY_DATE)   //定义生成的实体类中日期的类型 TIME_PACK=LocalDateTime;ONLY_DATE=Date;
                            .fileOverride()   //覆盖之前的文件
//                            .enableSwagger()   //开启 swagger 模式
                            .disableOpenDir();   //禁止打开输出目录，默认打开
                })
                //3、包配置
                .packageConfig(builder -> {
                    builder.parent(packageName) // 设置父包名
                            .moduleName(moduleName)   //设置模块包名
                            .entity("domain")   //pojo 实体类包名
                            .service("service") //Service 包名
                            .serviceImpl("serviceImpl") // ***ServiceImpl 包名
                            .mapper("mapper")   //Mapper 包名
                            .xml("mapper")  //Mapper XML 包名
                            .controller("controller") //Controller 包名
                            .other("utils"); //自定义文件包名
                    //.pathInfo(Collections.singletonMap(OutputFile.mapperXml,System.getProperty("user.dir") + "/"+ module+ "/src/main/resources/mapper"));
                })
                //4、策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的数据表名
//                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            //4.0、Domain策略配置
                            .entityBuilder()
                            .formatFileName("%sDomain")
                            .enableLombok() //开启 Lombok
                            .disableSerialVersionUID()  //不实现 Serializable 接口，不生产 SerialVersionUID
//                            .logicDeleteColumnName("del_flag")   //逻辑删除字段名
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE)
                            )   //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                            .enableTableFieldAnnotation()
                            //4.1、Mapper策略配置
                            .mapperBuilder()
                            .superClass(BaseMapper.class)   //设置父类
                            .formatMapperFileName("%sMapper")   //格式化 mapper 文件名称
                            .enableMapperAnnotation()       //开启 @Mapper 注解
                            .formatXmlFileName("%sMapper") //格式化 Xml 文件名称
                            //4.2、service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                    ;

                })
                //5、模板引擎配置
                .templateEngine(new VelocityTemplateEngine())
                //6、执行
                .execute();
    }
}
