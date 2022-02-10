package com.xyz.translator.api;

import com.ibm.watson.language_translator.v3.model.Language;
import com.jayway.jsonpath.JsonPath;
import com.xyz.translator.dto.LanguageOptionOutput;
import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class TranslateApiMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslateService translateServiceMock;

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

}
