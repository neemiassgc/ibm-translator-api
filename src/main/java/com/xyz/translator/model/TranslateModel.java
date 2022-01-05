package com.xyz.translator.model;

import com.xyz.translator.output.LanguageOutput;

import java.util.List;

public interface TranslateModel {

    List<LanguageOutput> listLanguages();

    String translate(String text, String source, String target);
}
