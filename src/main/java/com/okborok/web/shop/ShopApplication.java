package com.okborok.web.shop;

import com.okborok.web.shop.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebSecurityConfig.class})
public class ShopApplication {

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(ShopApplication.class, args);
    }
}
