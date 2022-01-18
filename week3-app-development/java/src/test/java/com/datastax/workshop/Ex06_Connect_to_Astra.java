package com.datastax.workshop;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;

@RunWith(JUnitPlatform.class)
public class Ex06_Connect_to_Astra implements DBConnection {

  /** Logger for the class. */
  private static Logger LOGGER = LoggerFactory.getLogger("Exercise 6");

  @Test
  public void should_connect_to_astra() {
    LOGGER.info("========================================");
    LOGGER.info("should_connect_to_astra");
    // Given
    Assertions.assertFalse(
      DBConnection.SECURE_CONNECT_BUNDLE.equals(""),
      "Please fill DBConnection class constants"
    );
    Assertions.assertFalse(
      DBConnection.KEYSPACE.equals(""),
      "Please fill DBConnection class constants"
    );
    Assertions.assertFalse(
      DBConnection.USERNAME.equals(""),
      "Please fill DBConnection class constants"
    );
    Assertions.assertFalse(
      DBConnection.PASSWORD.equals(""),
      "Please fill DBConnection class constants"
    );
    Assertions.assertTrue(
      new File(DBConnection.SECURE_CONNECT_BUNDLE).exists(),
      "File '" +
      DBConnection.SECURE_CONNECT_BUNDLE +
      "' has not been found\n" +
      "To run this sample you need to download the secure bundle file from ASTRA WebPage\n" +
      "More info here:"
    );
    LOGGER.info("File {} located", DBConnection.SECURE_CONNECT_BUNDLE);

    // When
    try (
      CqlSession cqlSession = CqlSession
        .builder()
        .withCloudSecureConnectBundle(
          Paths.get(DBConnection.SECURE_CONNECT_BUNDLE)
        )
        .withAuthCredentials(DBConnection.USERNAME, DBConnection.PASSWORD)
        .withKeyspace(DBConnection.KEYSPACE)
        .build()
    ) {
      // Then
      LOGGER.info("Connected with Keyspace {}", cqlSession.getKeyspace().get());
    }
    LOGGER.info("SUCCESS");
    LOGGER.info("========================================");
  }
}
