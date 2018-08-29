package com.rbkmoney.binbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.rbkmoney.binbase"})
public class BinbaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BinbaseApplication.class, args);
        int exitCode = SpringApplication.exit(context);
        System.exit(exitCode);
    }
}
