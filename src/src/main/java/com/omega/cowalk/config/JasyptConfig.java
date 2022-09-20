package com.omega.cowalk.config;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 민감한 정보들을 암호화하기 위한 설정 클래스 입니다.
 */
@Configuration
@RequiredArgsConstructor
public class JasyptConfig {

    private final Environment env;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(){

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(env.getProperty("jasypt.encryptor.password"));   //암호화할 때 사용하는 키
        config.setAlgorithm("PBEWithMD5AndDES");                      //암호화 알고리즘
        config.setKeyObtentionIterations("1000");                     //반복할 해싱 횟수
        config.setPoolSize("1");                                      //인스턴스 pool
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}
