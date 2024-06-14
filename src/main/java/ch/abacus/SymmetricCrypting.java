/*
 * SymmetricCrypting.java
 *
 * Creator:
 * 14.06.2024 08:33 josia.schweizer
 *
 * Maintainer:
 * 14.06.2024 08:33 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus;

import ch.abacus.components.PersonDTO;
import ch.abacus.decode.DecodeMessage;
import ch.abacus.encode.EncodeMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.crypto.SecretKey;
import java.util.Base64;

public class SymmetricCrypting {

  private final DecodeMessage decode = new DecodeMessage();
  private final EncodeMessage encode = new EncodeMessage();
  private final ObjectMapper mapper = new ObjectMapper();

  public SymmetricCrypting() {
    mapper.registerModule(new JavaTimeModule());
  }

  protected String encodePersonDTO(PersonDTO personDTO, SecretKey sessionKey) throws Exception {
    final var serializedPersonDTO = serialize(personDTO);
    return encode.encode(serializedPersonDTO, sessionKey);
  }

  protected PersonDTO decodePersonDTO(String encodedPersonDTO, SecretKey sessionKey) throws Exception {
    final var decodedPersonDTO = decode.decode(encodedPersonDTO, sessionKey);
    return deserialize(decodedPersonDTO);
  }

  protected SecuredMessage storeToSecuredMessage(PersonDTO personDTO) throws Exception {
    final var serializedPersonDTO = serialize(personDTO);
    final var sessionKey = SecurityUtil.createSessionKey();
    final var sessionKeyBase64 = Base64.getEncoder().encodeToString(sessionKey.getEncoded());
    final var encryptedPersonDTO = encode.encode(serializedPersonDTO, sessionKey);
    return new SecuredMessage(sessionKeyBase64, encryptedPersonDTO);
  }

  protected PersonDTO retrieveFromSecuredMessage(SecuredMessage securedMessage) throws Exception {
    final var sessionKeyString = Base64.getDecoder().decode(securedMessage.sessionKey());
    final var sessionKey = SecurityUtil.createSesssionKeyFromKeystring(sessionKeyString);

    final var encryptedPersonDTOStringBase64 = securedMessage.message();
    final var decryptedPersonDTO = decode.decode(encryptedPersonDTOStringBase64, sessionKey);
    return deserialize(decryptedPersonDTO);
  }

  String convertSecretMessageToJson(SecuredMessage securedMessage) throws Exception {
    return mapper.writeValueAsString(securedMessage);
  }

  SecuredMessage convertJsonToSecuredMessage(String securedMessageJson) throws Exception {
    return mapper.readValue(securedMessageJson, SecuredMessage.class);
  }

  private String serialize(PersonDTO personDTO) throws Exception {
    return mapper.writeValueAsString(personDTO);
  }

  private PersonDTO deserialize(String personDTO) throws Exception {
    return mapper.readValue(personDTO, PersonDTO.class);
  }
}