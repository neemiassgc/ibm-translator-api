package com.xyz.translator.services;

import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.Language;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslateService{

    @Autowired
    private LanguageTranslator languageTranslator;

    public List<Language> listLanguages() {
        return languageTranslator.listLanguages().execute().getResult().getLanguages();
    }

    public TranslationResult translate(final String text, final String from, final String to) {
        TranslateOptions translateOptions = new TranslateOptions.Builder()
            .addText(text)
            .source(from)
            .target(to)
            .build();

        return languageTranslator
            .translate(translateOptions)
            .execute()
            .getResult();
    }
}
