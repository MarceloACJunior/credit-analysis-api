package com.jazztech.creditanalysis.apiclient.ClientDto;


import java.util.UUID;
import lombok.Builder;

public record ClientDto(UUID id) {
    @Builder
    public ClientDto {
    }
}
