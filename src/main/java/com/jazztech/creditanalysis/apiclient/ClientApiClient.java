package com.jazztech.creditanalysis.apiclient;

import com.jazztech.creditanalysis.apiclient.ClientDto.ClientDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientApiClient", url = "localhost:8080/v1.0/clients")
public interface ClientApiClient {
    @GetMapping("/{clientId}")
    ClientDto getClientById(@PathVariable(value = "clientId") UUID clientId);
}
