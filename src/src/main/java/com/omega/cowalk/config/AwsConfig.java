package com.omega.cowalk.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String privateKey;


    @Bean
    AWSCredentials getAwsCredentials()
    {
        return new BasicAWSCredentials(accessKey, privateKey);
    }

    @Bean
    software.amazon.awssdk.auth.credentials.AwsCredentials getAwsCred(){
        return AwsBasicCredentials.create(accessKey, privateKey);
    }



}
