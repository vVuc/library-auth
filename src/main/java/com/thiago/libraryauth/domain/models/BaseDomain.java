package com.thiago.libraryauth.domain.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
public class BaseDomain {
    @EqualsAndHashCode.Include
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
