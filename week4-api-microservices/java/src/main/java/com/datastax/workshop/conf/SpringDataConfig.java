package com.datastax.workshop.conf;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDataConfig {
    
    @Value("${datastax.astra.secure-connect-bundle}")
    private File cloudSecureBundle;
    
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
        return builder -> builder.withCloudSecureConnectBundle(cloudSecureBundle.toPath());
    }
    
}
