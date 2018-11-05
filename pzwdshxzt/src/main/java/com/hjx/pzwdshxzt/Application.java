package com.hjx.pzwdshxzt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dwxqnswxl
 */
@SpringBootApplication
@MapperScan("com.hjx.pzwdshxzt.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
