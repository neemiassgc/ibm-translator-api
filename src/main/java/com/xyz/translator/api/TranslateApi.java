package com.xyz.translator.api;

import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import com.xyz.translator.error.ErrorResponse;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TranslateApi {

    private final TranslateService translateService;

    private final TranslateMapper translateMapper;

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

    @ExceptionHandler({ServiceResponseException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse;

        if (ex instanceof ServiceResponseException) {
            errorResponse = new ErrorResponse((ServiceResponseException) ex);
        }
        else errorResponse = new ErrorResponse(ex.getMessage(), ex.getCause(), HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
