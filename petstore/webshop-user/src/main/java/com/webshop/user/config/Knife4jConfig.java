package com.webshop.user.config;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;


@Configuration
@Profile("dev")
public class Knife4jConfig {

    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer(){
        return openApi -> {
            if(openApi.getTags()!=null){
                openApi.getTags().forEach(tag -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0, 100));
                    tag.setExtensions(map);
                });
            }
            if(openApi.getPaths()!=null){
                openApi.addExtension("x-test123", "333");
                openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(0, 100));
            }

        };
    }

    /**
     * 构建Api信息
     * @return
     */
    @Bean
    public OpenAPI customOpenAPI(){
        Contact acontact = new Contact();
        acontact.setName("XXX");
        acontact.setUrl("https://XXX.com");
        acontact.setEmail("XXX@qq.com");
        return new OpenAPI()
                .info(new Info()
                        .title("webshop-API")
                        .version("1.0")
                        .contact(acontact)
                        .description( "提供的Api")
                        .termsOfService("http://XXX.com")
                        .license(new License().name("XXX")
                                .url("http://XXX.com")));
    }

}
