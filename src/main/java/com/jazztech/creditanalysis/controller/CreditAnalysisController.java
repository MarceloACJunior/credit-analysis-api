package com.jazztech.creditanalysis.controller;

import com.jazztech.creditanalysis.controller.request.CreditAnalysisRequest;
import com.jazztech.creditanalysis.controller.response.CreditAnalysisResponse;
import com.jazztech.creditanalysis.service.CreditAnalysisService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/credit/analysis")
@RequiredArgsConstructor
public class CreditAnalysisController {
    private final CreditAnalysisService creditAnalysisService;

    @PostMapping
    @ResponseStatus(HttpStatus.FOUND)
    public CreditAnalysisResponse requestCreditAnalysis(@RequestBody CreditAnalysisRequest creditAnalysisRequest) {
        return creditAnalysisService.creditAnalysisRequest(creditAnalysisRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CreditAnalysisResponse> getAllAnalysis() {
        return creditAnalysisService.getAllCreditAnalysis();
    }

    @GetMapping("{creditAnalysisId}")
    @ResponseStatus(HttpStatus.FOUND)
    public CreditAnalysisResponse getCreditAnalysisById(@PathVariable UUID creditAnalysisId) {
        return creditAnalysisService.getCreditAnalysisById(creditAnalysisId);
    }
}
