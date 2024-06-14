/*
 * TestKryptographie.java
 *
 * Creator:
 * 13.06.2024 12:23 josia.schweizer
 *
 * Maintainer:
 * 13.06.2024 12:23 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus;

import ch.abacus.components.AddressDTO;
import ch.abacus.components.Gender;
import ch.abacus.components.PersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonDTOEncryptionTest {

  //private final SymmetricDecryption symmetricDecryption = new SymmetricDecryption();
  //private final SymmetricEncryption symmetricEncryption = new SymmetricEncryption();
  private final SecurityUtil securityUtil = new SecurityUtil();

  private static final String expected = """
      {
        "firstname": "Josia",
        "lastname": "Schweizer",
        "birthday": [
          2007,
          1,
          1
        ],
        "gender": "MALE",
        "addressDTO": {
          "street": "Schwarzenbach",
          "number": "2178",
          "city": "Gossau",
          "plz": 9200,
          "country": "Switzerland"
        }
      }""";

  @Test
  void testEncryptionDecryption() throws Exception {
    String text = "Hello, World!";
    SecretKey key = securityUtil.getSymetricKey();
    // String encrypted = symmetricEncryption.encrypt(text);
    // String decrypted = symmetricDecryption.decrypt(encrypted);
    assertEquals(text, decrypted);
  }

}