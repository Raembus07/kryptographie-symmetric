/*
 * EncodeMessage.java
 *
 * Creator:
 * 14.06.2024 07:40 josia.schweizer
 *
 * Maintainer:
 * 14.06.2024 07:40 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus.encode;

import ch.abacus.common.Const;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncodeMessage {

  public String encode(String message, SecretKey sessionKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    final var cipher = Cipher.getInstance(Const.AES_GCM_NO_PADDING);
    final var gcmParameter = getGcmParameter();
    final var gcmParameterBase64 = Base64.getEncoder().encodeToString(gcmParameter.getIV());
    cipher.init(Cipher.ENCRYPT_MODE, sessionKey, gcmParameter);
    final var encodedMessage = cipher.doFinal(message.getBytes());
    final var encodedMessageBase64 = Base64.getEncoder().encodeToString(encodedMessage);
    return gcmParameterBase64 + ":" + encodedMessageBase64;
  }

  public GCMParameterSpec getGcmParameter() throws NoSuchAlgorithmException, NoSuchPaddingException {
    final var random = SecureRandom.getInstanceStrong();
    final var iv = new byte[Cipher.getInstance(Const.SYMETRIC_ALGORITHM_SAVE).getBlockSize()];
    random.nextBytes(iv);
    return new GCMParameterSpec(Const.TAG_LENGTH, iv);
  }
}