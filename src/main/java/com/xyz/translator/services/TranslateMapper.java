package com.xyz.translator.services;

import com.ibm.watson.language_translator.v3.model.Language;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TranslateMapper {

    @Autowired
    private TranslateService translateService;

    public List<LanguageOptionOutput> mapLanguages(List<Language> languages) {
        return languages
            .stream()
            .filter(lang -> lang.isSupportedAsSource() && lang.isSupportedAsTarget())
            .map(lang ->
                new LanguageOptionOutput(
                    lang.getLanguageName(), lang.getNativeLanguageName(), lang.getLanguage()
                )
            ).collect(Collectors.toList());
    }

    public TranslateResponseOutput mapTranslateResponse(final TranslateRequestInput translateRequestInput) {
        final TranslationResult translationResult = translateService.translate(
            translateRequestInput.getText(),
            translateRequestInput.getSourceLanguage(),
            translateRequestInput.getTargetLanguage()
        );

        return new TranslateResponseOutput(translationResult.getTranslations().get(0).getTranslation());
    }
}
