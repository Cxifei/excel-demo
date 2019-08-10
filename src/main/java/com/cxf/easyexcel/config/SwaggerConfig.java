package com.cxf.easyexcel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 在线API配置文件
 * @Configuration 表明该类为配置文件类，即会在spring初始化的时候加载到spring的配置文件中
 * @EnableSwagger2 开启swagger
 *
 * @author admin
 * @date 2019-06-06 15:58
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @Bean 会在spring初始化时生成bean对象，类似xml配置文件中<bean></bean>标签
     * @return
     */
    @Bean
    public Docket getApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                // select() 返回一个ApiSelectorBuild对象，用于控制那些接口（controller）需要暴露给swagger
                .select()
                //扫描指定包中所有的controller定义的API，并产生文档内容（@ApiIgnore指定的除外）
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                // 创建文件的基本信息
                .apiInfo(apiInfo())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("统一接口API文档")
                .build();
    }

}
