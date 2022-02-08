package com.xyz.translator.services;

import com.ibm.watson.language_translator.v3.model.Language;
import com.xyz.translator.dto.LanguageOptionOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

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
}