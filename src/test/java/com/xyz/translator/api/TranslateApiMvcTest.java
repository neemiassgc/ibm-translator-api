package com.xyz.translator.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TranslateApiMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslateService translateServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TranslateMapper translateMapperMock;

    private Function<String, Response> httpResponse;

    @BeforeAll
    void init() {
        this.httpResponse = (body) ->
            new Response.Builder()
                .code(400)
                .body(ResponseBody.create(okhttp3.MediaType.parse("application/json"), body))
                .message("")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/translate").build())
                .build();
    }

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
        final Response httpResponse = this.httpResponse.apply("The parameter 'target' should not be empty");

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
        final Response httpResponse = this.httpResponse.apply("The parameter 'target' should not be empty");

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
    void shouldReturn400WhenTranslatingWithEmptyText() throws Exception {
        final TranslateRequestInput input = new TranslateRequestInput("", "en", "fr");
        final Response httpResponse = this.httpResponse.apply("Unable to validate payload size, the 'text' is empty.");

        given(this.translateMapperMock.mapTranslateResponse(any(TranslateRequestInput.class)))
            .willThrow(new BadRequestException(httpResponse));

        mockMvc.perform(post("/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Unable to validate payload size, the 'text' is empty."));
    }

    @Test
    void shouldReturn400WhenTranslatingWithoutText() throws Exception {
        final TranslateRequestInput input = new TranslateRequestInput(null, "en", "fr");
        final String message = "text cannot be null";

        given(this.translateMapperMock.mapTranslateResponse(any(TranslateRequestInput.class)))
            .willThrow(new IllegalArgumentException(message));

        final MvcResult mvcResult = mockMvc.perform(post("/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$").isMap()).andReturn();

        assertThat(mvcResult.getResolvedException()).isInstanceOf(IllegalArgumentException.class);
    }
}
