/*
 * SecurityUtil.java
 *
 * Creator:
 * 13.06.2024 16:10 josia.schweizer
 *
 * Maintainer:
 * 13.06.2024 16:10 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus;

import ch.abacus.common.EncryptionConst;

import javax.crypto.SecretKey;

public class SecurityUtil {

  public SecretKey createSessionKey() {
    final var secretKey = KeyGenerator.getInstance(EncryptionConst.AES).generateKey();
  }
}