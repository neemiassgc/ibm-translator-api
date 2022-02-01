package com.xyz.translator.configuration;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LanguageTranslatorConf {

    private static final String SERVICE_URL = System.getenv("IBM_CLOUD_SERVICE_URL");
    private static final String API_KEY = System.getenv("IBM_CLOUD_APIKEY");
    private static final String VERSION = System.getenv("IBM_CLOUD_VERSION");

    @Bean
    @Scope("singleton")
    public LanguageTranslator languageTranslator() {
        final IamAuthenticator authenticator = new IamAuthenticator.Builder().apikey(API_KEY).build();

        LanguageTranslator languageTranslator = new LanguageTranslator(VERSION, authenticator);
        languageTranslator.setServiceUrl(SERVICE_URL);

        return languageTranslator;
    }
}
