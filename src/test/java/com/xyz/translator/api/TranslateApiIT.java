package com.xyz.translator.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.xyz.translator.dto.TranslateRequestInput;
import com.xyz.translator.dto.TranslateResponseOutput;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TranslateApiIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnLanguageOptions() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(get("/languages"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[9].languageName").value("German"))
            .andExpect(jsonPath("$[9].nativeLanguageName").value("Deutsch"))
            .andExpect(jsonPath("$[9].language").value("de")).andReturn().getResponse();
    }

    @Test
    void shouldTranslateSuccessfullyWithStatus200() throws Exception {
        final TranslateRequestInput input = new TranslateRequestInput("Hello", "en", "fr");

        mockMvc.perform(post("/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.translatedText").value("Bonjour"));
    }

    @Test
    void shouldReturn400WhenTranslatingWithNullTargetLanguage() throws Exception {
        final TranslateRequestInput input = new TranslateRequestInput("Hello", "en", null);

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

        mockMvc.perform(post("/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Unable to validate payload size, the 'text' is empty."));
    }
}
