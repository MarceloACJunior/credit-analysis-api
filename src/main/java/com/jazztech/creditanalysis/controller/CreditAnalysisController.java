package com.jazztech.creditanalysis.controller;

import com.jazztech.creditanalysis.controller.request.CreditAnalysisRequest;
import com.jazztech.creditanalysis.controller.response.CreditAnalysisResponse;
import com.jazztech.creditanalysis.service.CreditAnalysisService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/credit/analysis")
@RequiredArgsConstructor
public class CreditAnalysisController {
    private final CreditAnalysisService creditAnalysisService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CreditAnalysisResponse requestCreditAnalysis(@RequestBody CreditAnalysisRequest creditAnalysisRequest) {
        return creditAnalysisService.creditAnalysisRequest(creditAnalysisRequest);
    }

    @GetMapping("{creditAnalysisId}")
    @ResponseStatus(HttpStatus.FOUND)
    public CreditAnalysisResponse getCreditAnalysisById(@PathVariable UUID creditAnalysisId) {
        return creditAnalysisService.getCreditAnalysisById(creditAnalysisId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<CreditAnalysisResponse> getCreditAnalysisBy(@RequestParam(value = "id", required = false) UUID creditAnalysisClientID,
                                                            @RequestParam(value = "cpf", required = false)
                                                            @Valid @CPF String creditAnalysisClientCPF) {
        if (creditAnalysisClientID != null) {
            return creditAnalysisService.getCreditAnalysisByClientId(creditAnalysisClientID);
        } else if (creditAnalysisClientCPF != null) {
            return creditAnalysisService.getCreditAnalysisByClientCPF(creditAnalysisClientCPF);
        }
        return creditAnalysisService.getAllCreditAnalysis();
    }
}
