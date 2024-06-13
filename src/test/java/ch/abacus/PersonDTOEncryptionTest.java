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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonDTOEncryptionTest {

  private PersonDTO person;
  private PersonDTOEncryption personDTOEncryption;
  private ObjectMapper mapper;

  @BeforeEach
  public void setUp() {
    person = new PersonDTO(1L, "Max", "Mustermann", LocalDate.of(2007, 1, 1), Gender.MALE, new AddressDTO(2L, "Musterstadt", "12345", "Musterstadt", 9292, "Musterland"));

    personDTOEncryption = new PersonDTOEncryption();
    mapper = new ObjectMapper();
  }

  @Test
  public void testStoreToSecuredMessage() throws CryptographieException, JsonProcessingException {
    personDTOEncryption.storeToSecuredMessage(person);
    SecuredMessage securedMessage = personDTOEncryption.getSecuredMessage();

    assertNotNull(securedMessage);
    assertNotNull(securedMessage.sessionKey());
    assertNotNull(securedMessage.message());

    String jsonSecuredMessage = personDTOEncryption.securedMessageToJson();
    assertNotNull(jsonSecuredMessage);

    SecuredMessage deserializedMessage = personDTOEncryption.jsonToSecuredMessage(jsonSecuredMessage);
    assertNotNull(deserializedMessage);
    assertEquals(securedMessage.sessionKey(), deserializedMessage.sessionKey());
    assertEquals(securedMessage.message(), deserializedMessage.message());

    PersonDTO decryptedPerson = personDTOEncryption.decryptSecuredMessage(deserializedMessage);

    assertNotNull(decryptedPerson);
    assertEquals(person.firstname(), decryptedPerson.firstname());
    assertEquals(person.lastname(), decryptedPerson.lastname());
    assertEquals(person.addressDTO().street(), decryptedPerson.addressDTO().street());
    assertEquals(person.addressDTO().city(), decryptedPerson.addressDTO().city());
    assertEquals(person.addressDTO().plz(), decryptedPerson.addressDTO().plz());
  }
}