/*
 * SymmetricEncryption.java
 *
 * Creator:
 * 13.06.2024 14:05 josia.schweizer
 *
 * Maintainer:
 * 13.06.2024 14:05 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus;

import ch.abacus.common.EncryptionConst;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SymmetricEncryption {

  private SecretKey secretKey;
  private Cipher cipher;

  public SymmetricEncryption() {
    try {
      secretKey = KeyGenerator.getInstance(EncryptionConst.AES).generateKey();
      cipher = Cipher.getInstance(EncryptionConst.AES);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SymmetricEncryption(String key) {
    try {
      byte[] decodedKey = Base64.getDecoder().decode(key);
      secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, EncryptionConst.AES);
      cipher = Cipher.getInstance(EncryptionConst.AES);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String encrypt(String originalMessage) throws CryptographieException {
    try {
      this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
      byte[] encryptedMessage;
      encryptedMessage = this.cipher.doFinal(originalMessage.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encryptedMessage);
    } catch (Exception e) {
      throw new CryptographieException("Encryption error", e);
    }
  }

  public String decrypt(String encryptedMessage) throws CryptographieException {
    try {
      this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
      byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage);
      byte[] originalMessage;
      originalMessage = this.cipher.doFinal(decodedMessage);
      return new String(originalMessage, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new CryptographieException("Decryption error", e);
    }
  }

  protected String getSecretKeyAsString() {
    return Base64.getEncoder().encodeToString(secretKey.getEncoded());
  }
}