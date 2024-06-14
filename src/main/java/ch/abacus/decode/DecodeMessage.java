/*
 * DecodeMessage.java
 *
 * Creator:
 * 14.06.2024 07:39 josia.schweizer
 *
 * Maintainer:
 * 14.06.2024 07:39 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus.decode;

import ch.abacus.common.Const;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DecodeMessage {

  public String decode(String messageBase64, SecretKey sessionKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    final var splittedMessageBase64 = messageBase64.split(":");
    final var gcmParameter = Base64.getDecoder().decode(splittedMessageBase64[0]);
    final var cipherText = Base64.getDecoder().decode(splittedMessageBase64[1]);

    final var gcmParameterSpec = new GCMParameterSpec(Const.TAG_LENGTH, gcmParameter);
    final var cipher = Cipher.getInstance(Const.AES_GCM_NO_PADDING);
    cipher.init(Cipher.DECRYPT_MODE, sessionKey, gcmParameterSpec);
    final var decodedMessage = cipher.doFinal(cipherText);

    String standardCharset = "ISO_8859_1";
    return new String(decodedMessage, standardCharset);
  }
}