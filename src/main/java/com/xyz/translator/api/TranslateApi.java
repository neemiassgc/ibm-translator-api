package com.xyz.translator.api;

import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Controller
public class TranslateApi {

    @Autowired
    private TranslateService translateService;

    @Autowired
    private TranslateMapper translateMapper;

    @GetMapping(path = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<LanguageOptionOutput>> getLanguageOptions() {
        return ResponseEntity.ok(translateMapper.mapLanguages(translateService.listLanguages()));
    }

    @PostMapping(
        path = "/translate",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public TranslateResponseOutput translate(@RequestBody final TranslateRequestInput translateRequestInput) {
        return translateMapper.mapTranslateResponse(translateRequestInput);
    }
}
