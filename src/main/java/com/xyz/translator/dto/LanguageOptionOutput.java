package com.xyz.translator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LanguageOptionOutput {

    private String languageName;
    private String nativeLanguageName;
    private String language;
}
