package com.jazztech.creditanalysis.apiclient;

<<<<<<< HEAD
import com.jazztech.creditanalysis.apiclient.ClientDto.ClientDto;
=======
import com.jazztech.creditanalysis.apiclient.clientdto.ClientDto;
>>>>>>> feature/credit-analise
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clientApiClient", url = "localhost:8080/v1.0/clients")
public interface ClientApiClient {

    @GetMapping("/{clientId}")
    ClientDto getClientById(@PathVariable(value = "clientId") UUID clientId);

    @GetMapping
    List<ClientDto> getClientByCPF(@RequestParam(value = "cpf") String clientCPF);
}
