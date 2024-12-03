package com.kong.cc.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.kong.cc.CafeconnectBackApplication;

import java.io.IOException;
import java.io.InputStream;

public class TranslateUtil {

    public static String translate(String koWord) throws IOException {
        try {
            // JSON 키 파일을 resources 디렉토리에서 로드
            InputStream keyStream = CafeconnectBackApplication.class.getClassLoader().getResourceAsStream("translation-key.json");
            if (keyStream == null) {
                throw new IllegalStateException("Cannot find translation-key.json in resources directory");
            }

            // Google Cloud Translation API 클라이언트 초기화
            GoogleCredentials credentials = GoogleCredentials.fromStream(keyStream);
            Translate translate = TranslateOptions.newBuilder().setCredentials(credentials).build().getService();

            // 번역할 텍스트
            String koreanText = koWord;

            // 번역 요청
            Translation translation = translate.translate(
                    koreanText,
                    Translate.TranslateOption.sourceLanguage("ko"), // 소스 언어: 한국어
                    Translate.TranslateOption.targetLanguage("en")  // 타겟 언어: 영어
            );

            // 번역 결과 출력
            System.out.println("Original: " + koreanText);
            System.out.println("Translated: " + translation.getTranslatedText());
            return translation.getTranslatedText();
        } catch (Exception e) {

            throw e;
        }
    }
}
