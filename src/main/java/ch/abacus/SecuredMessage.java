package ch.abacus;

import jakarta.annotation.Nonnull;

public record SecuredMessage(@Nonnull String sessionKey, @Nonnull String message) {

}
