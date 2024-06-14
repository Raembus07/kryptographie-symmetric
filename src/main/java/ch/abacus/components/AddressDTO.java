/*
 * AddressDTO.java
 *
 * Creator:
 * 13.06.2024 14:07 josia.schweizer
 *
 * Maintainer:
 * 13.06.2024 14:07 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus.components;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;

public record AddressDTO(@Nullable Long id, @Nonnull String street, @Nonnull String number, @Nonnull String city,
                         @Nonnull Integer plz, @Nonnull String country) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressDTO that = (AddressDTO) o;
    return Objects.equals(street, that.street) && Objects.equals(number, that.number) && Objects.equals(city, that.city) && Objects.equals(plz, that.plz) && Objects.equals(country, that.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, number, city, plz, country);
  }
}