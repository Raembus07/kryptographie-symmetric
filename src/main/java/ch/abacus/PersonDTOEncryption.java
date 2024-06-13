/*
 * JsonEncryption.java
 *
 * Creator:
 * 13.06.2024 14:12 josia.schweizer
 *
 * Maintainer:
 * 13.06.2024 14:12 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus;

import ch.abacus.common.ErrorConst;
import ch.abacus.components.PersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class PersonDTOEncryption {

  private SymmetricEncryption symmetricEncryption = new SymmetricEncryption();
  private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  private SecuredMessage securedMessage;

  protected String encrpyt(PersonDTO personDTO) throws CryptographieException {
    return symmetricEncryption.encrypt(serialize(personDTO));
  }

  protected PersonDTO decrypt(String encryptedPersonDTO) throws CryptographieException {
    return deserialize(symmetricEncryption.decrypt(encryptedPersonDTO));
  }

  private String serialize(PersonDTO personDTO) throws CryptographieException {
    try {
      return mapper.writeValueAsString(personDTO);
    } catch (JsonProcessingException e) {
      throw new CryptographieException(ErrorConst.ERROR_SERIALIZE_PERSON_DTO, e);
    }
  }

  private PersonDTO deserialize(String personDTO) throws CryptographieException {
    try {
      return mapper.readValue(personDTO, PersonDTO.class);
    } catch (JsonProcessingException e) {
      throw new CryptographieException(ErrorConst.ERROR_DESERIALIZE_PERSON_DTO, e);
    }
  }

  protected void storeToSecuredMessage(PersonDTO personDTO) throws CryptographieException {
    securedMessage = new SecuredMessage(symmetricEncryption.getSecretKeyAsString(), encrpyt(personDTO));
  }

  public SecuredMessage getSecuredMessage() {
    return securedMessage;
  }

  public String securedMessageToJson() throws CryptographieException {
    try {
      return mapper.writeValueAsString(securedMessage);
    } catch (JsonProcessingException e) {
      throw new CryptographieException(ErrorConst.ERROR_SERIALIZE_PERSON_DTO, e);
    }
  }

  public SecuredMessage jsonToSecuredMessage(String json) throws CryptographieException {
    try {
      return mapper.readValue(json, SecuredMessage.class);
    } catch (JsonProcessingException e) {
      throw new CryptographieException("Error deserializing JSON to SecuredMessage", e);
    }
  }

  public PersonDTO decryptSecuredMessage(SecuredMessage securedMessage) throws CryptographieException {
    SymmetricEncryption decryptor = new SymmetricEncryption(securedMessage.sessionKey());
    String decryptedData = decryptor.decrypt(securedMessage.message());
    return deserialize(decryptedData);
  }
}