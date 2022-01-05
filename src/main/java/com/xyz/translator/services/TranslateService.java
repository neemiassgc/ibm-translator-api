package com.xyz.translator.services;

import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.xyz.translator.composers.SingletonLanguageTranslator;
import com.xyz.translator.model.TranslateModel;
import com.xyz.translator.output.LanguageOutput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslateService implements TranslateModel {

    private static final LanguageTranslator languageTranslator = SingletonLanguageTranslator.getInstance();

    @Override
    public List<LanguageOutput> listLanguages() {
        return languageTranslator.listLanguages().execute().getResult().getLanguages()
            .stream()
                .filter(language -> language.isSupportedAsSource() && language.isSupportedAsTarget())
                .map(language -> new LanguageOutput(language.getLanguageName(),
                    language.getNativeLanguageName(),
                    language.getLanguage())).collect(Collectors.toList());
    }

    @Override
    public String translate(final String text, final String sourceLanguage, final String targetLanguage) {
        TranslateOptions translateOptions = new TranslateOptions.Builder()
            .addText(text)
            .source(sourceLanguage)
            .target(targetLanguage)
            .build();

        return languageTranslator
            .translate(translateOptions)
            .execute()
            .getResult()
            .getTranslations()
            .get(0)
            .getTranslation();
    }
}
