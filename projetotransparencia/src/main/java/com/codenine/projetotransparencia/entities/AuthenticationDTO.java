package com.codenine.projetotransparencia.entities;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(
        @NotNull String login,
        @NotNull String password
) {}
