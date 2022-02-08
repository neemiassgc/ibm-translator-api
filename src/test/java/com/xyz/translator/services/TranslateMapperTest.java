package com.xyz.translator.services;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

final class TranslateMapperTest {

    private TranslateService translateServiceMock;
    private TranslateMapper translateMapperUnderTest;

    @BeforeEach
    void setup() {
        this.translateServiceMock = Mockito.mock(TranslateService.class);
        this.translateMapperUnderTest = new TranslateMapper(this.translateServiceMock);
    }
}