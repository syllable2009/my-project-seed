package com.company.project.config;

import static com.company.project.core.ProjectConstant.BASE_PACKAGE;
import static com.company.project.core.ProjectConstant.MAPPER_INTERFACE_REFERENCE;
import static com.company.project.core.ProjectConstant.MODEL_PACKAGE;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.github.pagehelper.PageHelper;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Mybatis & Mapper & PageHelper 配置
 */
@Configuration
public class MybatisConfigurer {

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(MODEL_PACKAGE);

        //配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero", "true");//当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
        properties.setProperty("offsetAsPageNum", "false");//设置为true时，会将RowBounds第一个參数offset当成pageNum页码使用
        properties.setProperty("rowBoundsWithCount", "true");//设置为true时，使用RowBounds分页会进行count查询
        properties.setProperty("reasonable", "false");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("dialect", "mysql"); //配置mysql数据库的方言
//        properties.setProperty("returnPageInfo", "none");
        properties.setProperty("params", "count=countSql");
        properties.setProperty("supportMethodsArguments", "true");//支持通过 Mapper 接口参数来传递分页参数
        pageHelper.setProperties(properties);

        //添加插件
        factory.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/**/*Mapper.xml"));
        return factory.getObject();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage(BASE_PACKAGE + ".dao");

        //配置通用Mapper，详情请查阅官方文档
        Properties properties = new Properties();
        properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        properties.setProperty("notEmpty", "false");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }

}

