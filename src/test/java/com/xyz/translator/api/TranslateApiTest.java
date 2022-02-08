package com.xyz.translator.api;

import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibm.watson.language_translator.v3.model.Language;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import com.xyz.translator.error.ErrorResponse;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

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

    @Test
    void translateTest() {
        // given
        final TranslateRequestInput translateRequestInput = new TranslateRequestInput();
        final TranslateResponseOutput translateResponseOutput = new TranslateResponseOutput();
        given(translateMapperMock.mapTranslateResponse(any(TranslateRequestInput.class)))
            .willReturn(translateResponseOutput);

        // when
        final TranslateResponseOutput actualResult = translateApi.translate(translateRequestInput);

        // then
        assertThat(actualResult).isNotNull();

        verify(translateMapperMock, times(1))
            .mapTranslateResponse(any(TranslateRequestInput.class));
        verify(translateMapperMock, only())
                .mapTranslateResponse(any(TranslateRequestInput.class));
    }

    @Test
    void handleExceptionTest() {
        // given
        final Response response = new Response.Builder()
            .request(new Request.Builder().get().url("https://google.com").build())
            .protocol(Protocol.HTTP_1_1)
            .body(ResponseBody.create(MediaType.get("application/json"), "{'name':'Bob'}"))
            .code(0)
            .message("default")
            .sentRequestAtMillis(200)
            .receivedResponseAtMillis(500).build();

        final ServiceResponseException serviceResponseException = new ServiceResponseException(200, response);

        // when
        final ResponseEntity<ErrorResponse> actualResponse = translateApi.handleException(serviceResponseException);

        // then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getBody()).isNotNull();
        assertThat(actualResponse.getStatusCodeValue()).isEqualTo(200);
    }
}