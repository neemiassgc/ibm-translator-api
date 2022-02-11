package com.xyz.translator.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class TranslateApiMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslateService translateServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TranslateMapper translateMapperMock;

    @Test
    void shouldReturnLanguageOptions() throws Exception {
        given(this.translateServiceMock.listLanguages()).willReturn(Collections.emptyList());

        final List<LanguageOptionOutput> languageOptionOutputList =
            List.of(
                new LanguageOptionOutput("English", "English", "en"),
                new LanguageOptionOutput("French", "French", "fr")
            );
        given(this.translateMapperMock.mapLanguages(anyList())).willReturn(languageOptionOutputList);

        mockMvc.perform(get("/languages"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].languageName").value("English"))
            .andExpect(jsonPath("$[0].nativeLanguageName").value("English"))
            .andExpect(jsonPath("$[0].language").value("en"));
    }

    @Test
    void shouldTranslateSuccessfully() throws Exception {
        final TranslateRequestInput input = new TranslateRequestInput("Hello", "en", "fr");
        final TranslateResponseOutput output = new TranslateResponseOutput("Bonjour");

        given(this.translateMapperMock.mapTranslateResponse(any(TranslateRequestInput.class))).willReturn(output);

        mockMvc.perform(post("/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.translatedText").value("Bonjour"));

        verify(this.translateMapperMock, times(1)).mapTranslateResponse(any(TranslateRequestInput.class));
        verify(this.translateMapperMock, only()).mapTranslateResponse(any(TranslateRequestInput.class));
    }

    @Test
    void shouldReturn400WhenTranslatingWithNullTargetLanguage() throws Exception {
        final TranslateRequestInput input = new TranslateRequestInput("Hello", "en", null);
        final Response httpResponse = new Response.Builder()
            .code(400)
            .body(ResponseBody.create(
                okhttp3.MediaType.parse("application/json"),
                "The parameter 'target' should not be empty")
            )
            .message("")
            .protocol(Protocol.HTTP_1_1)
            .request(new okhttp3.Request.Builder().url("http://localhost/translate").build())
            .build();

        given(this.translateMapperMock.mapTranslateResponse(any(TranslateRequestInput.class)))
            .willThrow(new BadRequestException(httpResponse));

        mockMvc.perform(post("/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("The parameter 'target' should not be empty"));
    }

    @Test
    void shouldReturn400WhenTranslatingWithEmptyTargetLanguage() throws Exception {
        final TranslateRequestInput input = new TranslateRequestInput("Hello", "en", "");
        final Response httpResponse = new Response.Builder()
            .code(400)
            .body(ResponseBody.create(
                okhttp3.MediaType.parse("application/json"),
                "The parameter 'target' should not be empty")
            )
            .message("")
            .protocol(Protocol.HTTP_1_1)
            .request(new okhttp3.Request.Builder().url("http://localhost/translate").build())
            .build();

        given(this.translateMapperMock.mapTranslateResponse(any(TranslateRequestInput.class)))
            .willThrow(new BadRequestException(httpResponse));

        mockMvc.perform(post("/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("The parameter 'target' should not be empty"));
    }
}
