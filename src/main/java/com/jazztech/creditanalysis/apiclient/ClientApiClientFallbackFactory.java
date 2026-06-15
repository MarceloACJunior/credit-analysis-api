package com.jazztech.creditanalysis.apiclient;

import com.jazztech.creditanalysis.apiclient.ClientDto.ClientDto;
import com.jazztech.creditanalysis.handler.exceptions.ClientApiUnavailableException;
import com.jazztech.creditanalysis.handler.exceptions.ClientNotFoundException;
import feign.FeignException;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientApiClientFallbackFactory implements FallbackFactory<ClientApiClient> {
    private static final int CLIENT_ERROR_START = 400;
    private static final int SERVER_ERROR_START = 500;

    @Override
    public ClientApiClient create(Throwable cause) {
        return new ClientApiClient() {
            @Override
            public ClientDto getClientById(UUID clientId) {
                if (isClientError(cause)) {
                    throw new ClientNotFoundException("Client not found by id %s".formatted(clientId));
                }
                throw new ClientApiUnavailableException("client-api is unavailable, please try again later", cause);
            }

            @Override
            public List<ClientDto> getClientByCPF(String clientCPF) {
                throw new ClientApiUnavailableException("client-api is unavailable, please try again later", cause);
            }
        };
    }

    private boolean isClientError(Throwable cause) {
        return cause instanceof FeignException fe && fe.status() >= CLIENT_ERROR_START && fe.status() < SERVER_ERROR_START;
    }
}
