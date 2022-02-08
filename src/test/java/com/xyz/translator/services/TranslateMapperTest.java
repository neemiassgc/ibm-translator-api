package com.xyz.translator.services;

import com.ibm.watson.language_translator.v3.model.Language;
import com.ibm.watson.language_translator.v3.model.Translation;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

final class TranslateMapperTest {

    private TranslateService translateServiceMock;
    private TranslateMapper translateMapperUnderTest;

    @BeforeEach
    void setup() {
        this.translateServiceMock = Mockito.mock(TranslateService.class);
        this.translateMapperUnderTest = new TranslateMapper(this.translateServiceMock);
    }

    @Test
    void mapLanguagesTest() {
        // given
        final List<Language> languages = new Vector<>(4);
        for (int i = 0; i < 4; i++) {
            Language lang = new Language() {
                @Override
                public Boolean isSupportedAsSource() {
                    return true;
                }

                @Override
                public Boolean isSupportedAsTarget() {
                    return true;
                }

                @Override
                public String getLanguage() {
                    return "en";
                }

                @Override
                public String getLanguageName() {
                    return "English";
                }

                @Override
                public String getNativeLanguageName() {
                    return "English";
                }
            };
            languages.add(lang);
        }

        // when
        final List<LanguageOptionOutput> actualList = this.translateMapperUnderTest.mapLanguages(languages);

        // then
        assertThat(actualList).isNotNull();
        assertThat(actualList).allSatisfy(lang -> assertThat(lang).isNotNull());
        assertThat(actualList).hasSize(4);
    }

    @Test
    void mapLanguagesWithEmptyList() {
        // given
        final List<Language> emptyList = Collections.emptyList();

        // when
        final List<LanguageOptionOutput> actualList = this.translateMapperUnderTest.mapLanguages(emptyList);

        // then
        assertThat(actualList).isNotNull();
        assertThat(actualList).isEmpty();
    }

    @Test
    void mapTranslateResponseTest() {
        // given
        final TranslateRequestInput requestInput = new TranslateRequestInput("Hello", "en", "fr");
        final TranslationResult translationResult = new TranslationResult() {
            @Override
            public List<Translation> getTranslations() {
                return List.of(new Translation());
            }
        };
        Mockito.when(this.translateServiceMock.translate(any(String.class), any(String.class), any(String.class)))
            .thenReturn(translationResult);

        // when
        final TranslateResponseOutput actual = this.translateMapperUnderTest.mapTranslateResponse(requestInput);

        // then
        assertThat(actual).isNotNull();

        verify(this.translateServiceMock, times(1))
            .translate(any(String.class), any(String.class), any(String.class));
        verify(this.translateServiceMock, only())
            .translate(any(String.class), any(String.class), any(String.class));
    }
}