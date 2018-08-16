package com.rbkmoney.binbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.rbkmoney.binbase"})
public class BinbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BinbaseApplication.class, args);
    }
}
