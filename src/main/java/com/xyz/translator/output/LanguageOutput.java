package com.xyz.translator.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LanguageOutput {

    private final String languageName;
    private final String nativeLanguageName;
    private final String language;
}
