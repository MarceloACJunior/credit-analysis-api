package com.jazztech.creditanalysis.apiclient.clientdto;


import java.util.UUID;
import lombok.Builder;

public record ClientDto(UUID id) {
    @Builder
    public ClientDto {
    }
}
