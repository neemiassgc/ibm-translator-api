package com.xyz.translator.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TranslateResponseOutput {

    private final String translatedText;
}
