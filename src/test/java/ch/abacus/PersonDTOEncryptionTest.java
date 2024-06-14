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
import ch.abacus.decode.DecodeMessage;
import ch.abacus.encode.EncodeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonDTOEncryptionTest {

  private SymmetricCrypting symmetricCrypting;
  private DecodeMessage decode;
  private EncodeMessage encode;

  private final AddressDTO addressDTO = new AddressDTO(2L, "Schwarzenbach", "2178", "Gossau", 9200, "Switzerland");
  private final PersonDTO personDTO = new PersonDTO(1L, "Josia", "Schweizer", LocalDate.of(2007, 1, 1), Gender.MALE, addressDTO);

  @BeforeEach
  public void setUp() {
    symmetricCrypting = new SymmetricCrypting();
    decode = new DecodeMessage();
    encode = new EncodeMessage();
  }

  @Test
  void testTaskOne() throws Exception {
    String text = "Hello World!";
    SecretKey sessionKey = SecurityUtil.createSessionKey();
    String encryptedText = encode.encode(text, sessionKey);
    String decryptedText = decode.decode(encryptedText, sessionKey);
    assertEquals(text, decryptedText);
  }

  @Test
  void testTaskTwo() throws Exception{
    SecretKey sessionKey = SecurityUtil.createSessionKey();
    String encryptedPersonDTO = symmetricCrypting.encodePersonDTO(personDTO, sessionKey);
    PersonDTO decryptedPersonDTO = symmetricCrypting.decodePersonDTO(encryptedPersonDTO, sessionKey);

    assertEquals(personDTO, decryptedPersonDTO);
  }


  @Test
  void testTaskThree() throws Exception{
    SecuredMessage securedmessage = symmetricCrypting.storeToSecuredMessage(personDTO);
    PersonDTO decryptedPersonDTO = symmetricCrypting.retrieveFromSecuredMessage(securedmessage);

    assertEquals(personDTO, decryptedPersonDTO);
  }

  @Test
  void testTastkFour() throws Exception{
    SecuredMessage securedmessage = symmetricCrypting.storeToSecuredMessage(personDTO);
    String securedMessageJson = symmetricCrypting.convertSecretMessageToJson(securedmessage);
    SecuredMessage securedMessage = symmetricCrypting.convertJsonToSecuredMessage(securedMessageJson);
    PersonDTO decryptedPersonDTO = symmetricCrypting.retrieveFromSecuredMessage(securedMessage);

    assertEquals(personDTO, decryptedPersonDTO);
  }
}