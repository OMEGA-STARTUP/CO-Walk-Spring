package com.omega.cowalk.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        // Firebase를 사용하기 위한 사용자 인증정보
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("co-walk-firebase-service-account.json").getInputStream());
        //Firebase 옵션객체에 인증정보 세팅
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        //인증정보를 바탕으로 앱 실행
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "co-walk");
        return FirebaseMessaging.getInstance(app);
    }
}
