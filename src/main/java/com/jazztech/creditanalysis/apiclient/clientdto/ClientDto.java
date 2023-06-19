package com.jazztech.creditanalysis.apiclient.clientdto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ClientDto(UUID id) {
}
