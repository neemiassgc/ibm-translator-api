package com.xyz.translator.api;

import com.xyz.translator.model.TranslateModel;
import com.xyz.translator.output.LanguageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TranslateApi {

    @Autowired
    private TranslateModel translateModel;

    @GetMapping(path = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<LanguageOutput>> getLanguageOptions() {
        return ResponseEntity.ok(translateModel.listLanguages());
    }

    @GetMapping(
        path = "/translate",
        params = {"source", "target"},
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String translate(
        @RequestParam(value = "source", defaultValue = "en") final String source,
        @RequestParam(value = "target", defaultValue = "pt") final String target,
        @RequestBody final String text
    ) {
        return translateModel.translate(text, source, target);
    }
}
