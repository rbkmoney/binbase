package com.rbkmoney.binbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.rbkmoney.binbase"})
public class BinbaseApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BinbaseApplication.class, args);

        boolean needShutdown = Boolean.parseBoolean(context.getBeanFactory().resolveEmbeddedValue("${batch.shutdown_after_execute}"));
        if (needShutdown) {
            int exitCode = SpringApplication.exit(context);
            System.exit(exitCode);
        }
    }
}
