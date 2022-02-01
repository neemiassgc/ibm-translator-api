package com.xyz.translator.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public final class TranslateRequestInput {

    private final String text;
    private final String sourceLanguage;
    private final String targetLanguage;
}
