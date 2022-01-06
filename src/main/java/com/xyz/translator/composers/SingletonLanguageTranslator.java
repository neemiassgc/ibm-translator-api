package com.xyz.translator.composers;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;

import java.util.Objects;

public final class SingletonLanguageTranslator {

    private static final String SERVICE_URL = System.getenv("IBM_CLOUD_SERVICE_URL");
    private static final String API_KEY = System.getenv("IBM_CLOUD_APIKEY");
    private static final String VERSION = System.getenv("IBM_CLOUD_VERSION");

    private static LanguageTranslator INSTANCE;

    public static LanguageTranslator getInstance() {
        if (!Objects.isNull(INSTANCE)) return INSTANCE;

        final IamAuthenticator authenticator = new IamAuthenticator.Builder().apikey(API_KEY).build();

        LanguageTranslator languageTranslator = new LanguageTranslator(VERSION, authenticator);
        languageTranslator.setServiceUrl(SERVICE_URL);

        return languageTranslator;
    }
}