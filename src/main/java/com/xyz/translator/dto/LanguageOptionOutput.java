package com.xyz.translator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageOptionOutput {

    private String languageName;
    private String nativeLanguageName;
    private String language;
}
