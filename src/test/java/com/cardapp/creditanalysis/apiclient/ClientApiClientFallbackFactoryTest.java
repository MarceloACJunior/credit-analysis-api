package com.cardapp.creditanalysis.apiclient;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cardapp.creditanalysis.handler.exceptions.ClientApiUnavailableException;
import com.cardapp.creditanalysis.handler.exceptions.ClientNotFoundException;
import feign.FeignException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ClientApiClientFallbackFactoryTest {

    private final ClientApiClientFallbackFactory factory = new ClientApiClientFallbackFactory();

    @Test
    void getClientById_throws_ClientNotFoundException_when_cause_is_4xx() {
        final FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(404);

        final ClientApiClient fallback = factory.create(feignException);

        assertThrows(ClientNotFoundException.class, () -> fallback.getClientById(UUID.randomUUID()));
    }

    @Test
    void getClientById_throws_ClientApiUnavailableException_when_cause_is_outage() {
        final ClientApiClient fallback = factory.create(new RuntimeException("connection refused"));

        assertThrows(ClientApiUnavailableException.class, () -> fallback.getClientById(UUID.randomUUID()));
    }

    @Test
    void getClientByCPF_throws_ClientApiUnavailableException() {
        final ClientApiClient fallback = factory.create(new RuntimeException("connection refused"));

        assertThrows(ClientApiUnavailableException.class, () -> fallback.getClientByCPF("12345678900"));
    }
}
