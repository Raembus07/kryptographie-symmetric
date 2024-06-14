/*
 * PersonDTO.java
 *
 * Creator:
 * 13.06.2024 14:08 josia.schweizer
 *
 * Maintainer:
 * 13.06.2024 14:08 josia.schweizer
 *
 * Last Modification:
 * $Id:$
 *
 * Copyright (c) 2024 ABACUS Research AG, All Rights Reserved
 */
package ch.abacus.components;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Objects;

public record PersonDTO(@Nullable Long id, @Nonnull String firstname, @Nonnull String lastname,
                        @Nonnull LocalDate birthday, @Nonnull Gender gender, @Nonnull AddressDTO addressDTO) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonDTO personDTO = (PersonDTO) o;
    return Objects.equals(firstname, personDTO.firstname) && Objects.equals(lastname, personDTO.lastname) && Objects.equals(birthday, personDTO.birthday) && gender == personDTO.gender && Objects.equals(addressDTO, personDTO.addressDTO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname, birthday, gender, addressDTO);
  }
}