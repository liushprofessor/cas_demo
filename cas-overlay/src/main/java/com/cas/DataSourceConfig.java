package com.cas;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


/**
 * @author Liush
 * @description
 * @date 2019/12/20 14:07
 **/


public class DataSourceConfig {



    @Bean
    public DataSource dataSource(){


        DriverManagerDataSource dataSource= new DriverManagerDataSource("jdbc:mysql://192.168.169.94:3306/mysql?serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false","root","root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.cas");
        return mapperScannerConfigurer;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setMapperLocations();
        factoryBean.setDataSource(dataSource());
        ResourcePatternResolver resourcePatternResolver=new PathMatchingResourcePatternResolver();
        resourcePatternResolver.getResources("classpath*:mapper/*.xml");
        factoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/*.xml"));
        return factoryBean.getObject();
    }







}

