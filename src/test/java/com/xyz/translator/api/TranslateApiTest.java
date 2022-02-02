package com.xyz.translator.api;

import com.ibm.watson.language_translator.v3.model.Language;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

final class TranslateApiTest {

    private TranslateApi translateApi;
    private TranslateService translateServiceMock;
    private TranslateMapper translateMapperMock;

    @BeforeEach
    void setup() {
        this.translateServiceMock = mock(TranslateService.class);
        this.translateMapperMock = mock(TranslateMapper.class);
        this.translateApi = new TranslateApi(this.translateServiceMock, this.translateMapperMock);
    }

    @Test
    void getLanguageOptionsTest() {
        // given
        final List<Language> languageList = List.of(new Language(), new Language());
        given(this.translateServiceMock.listLanguages()).willReturn(languageList);

        final List<LanguageOptionOutput> languageOptionOutputList =
            List.of(new LanguageOptionOutput(), new LanguageOptionOutput());
        given(this.translateMapperMock.mapLanguages(anyList())).willReturn(languageOptionOutputList);

        // when
        final ResponseEntity<List<LanguageOptionOutput>> response = translateApi.getLanguageOptions();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);

        verify(translateServiceMock, times(1)).listLanguages();
        verify(translateMapperMock, times(1)).mapLanguages(anyList());
    }
}