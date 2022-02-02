package com.xyz.translator.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class TranslateRequestInput {

    private String text;
    private String sourceLanguage;
    private String targetLanguage;
}
