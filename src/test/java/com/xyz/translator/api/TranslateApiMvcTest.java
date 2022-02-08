package com.xyz.translator.api;

import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class TranslateApiMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslateService translateServiceMock;

    @MockBean
    private TranslateMapper translateMapperMock;

}
