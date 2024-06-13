/*
 * SymmetricDecryption.java
 *
 * Creator:
 * 13.06.2024 15:59 josia.schweizer
 *
 * Maintainer:
 * 13.06.2024 15:59 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SymmetricDecryption {

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
}