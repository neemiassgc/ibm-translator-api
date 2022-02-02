package com.xyz.translator.api;

import com.xyz.translator.services.TranslateMapper;
import com.xyz.translator.services.TranslateService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

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
}