package com.datastax.workshop;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * Connecticity to ASTRA
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="/application.properties")
public class Ex1_ConnectivityToAstraExplicitTest {
    
    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger(Ex1_ConnectivityToAstraExplicitTest.class);
    
    @Value("${spring.data.cassandra.username}")
    private String username;
    
    @Value("${spring.data.cassandra.password}")
    private String password;
    
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;
    
    @Value("${datastax.astra.secure-connect-bundle}")
    private String cloudSecureBundle;
     
    @Test
    @DisplayName("Test connectivity to Astra explicit values")
    public void should_connect_to_Astra() {
        
        // Given interface is properly populated
        Assertions.assertTrue(new File(cloudSecureBundle).exists(), 
                    "File '" + cloudSecureBundle + "' has not been found\n"
                    + "To run this sample you need to download the secure bundle file from ASTRA WebPage\n"
                    + "More info here:");
        
        // When connecting to ASTRA
        try (CqlSession cqlSession = CqlSession.builder()
                //.addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withCloudSecureConnectBundle(Paths.get(cloudSecureBundle))
                .withAuthCredentials(username, password)
                .withKeyspace(keyspace)
                .build()) {
            
            // Then connection is successfull
            LOGGER.info(" + [OK] - Connection Established to Astra with Keyspace {}", 
                    cqlSession.getKeyspace().get());
        }
    }
}
