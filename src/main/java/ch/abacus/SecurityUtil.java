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

import ch.abacus.common.Const;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

  public static SecretKey createSessionKey() throws NoSuchAlgorithmException {
    return KeyGenerator.getInstance(Const.AES).generateKey();
  }

  public static SecretKey createSesssionKeyFromKeystrign(String keyString){
    return new SecretKeySpec(keyString.getBytes(), Const.AES);
  }
}