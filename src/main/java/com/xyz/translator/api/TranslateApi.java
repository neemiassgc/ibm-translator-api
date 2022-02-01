package com.xyz.translator.api;

import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import com.xyz.translator.error.ErrorResponse;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class TranslateApi {

    @Autowired
    private TranslateService translateService;

    @Autowired
    private TranslateMapper translateMapper;

    @GetMapping(path = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LanguageOptionOutput>> getLanguageOptions() {
        return ResponseEntity.ok(translateMapper.mapLanguages(translateService.listLanguages()));
    }

    @PostMapping(
        path = "/translate",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public TranslateResponseOutput translate(@RequestBody final TranslateRequestInput translateRequestInput) {
        return translateMapper.mapTranslateResponse(translateRequestInput);
    }

    @ExceptionHandler(ServiceResponseException.class)
    public ResponseEntity<ErrorResponse> handleException(final ServiceResponseException sre) {
        final ErrorResponse errorResponse = new ErrorResponse(sre);
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
