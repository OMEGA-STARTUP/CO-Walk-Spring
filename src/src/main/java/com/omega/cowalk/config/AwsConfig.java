package com.omega.cowalk.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String access_key;

    @Value("${cloud.aws.credentials.access-key}")
    private String private_key;


    @Bean
    AWSCredentials getAwsCredentials()
    {
        return new BasicAWSCredentials("AKIATERKWI6SJWFKSQGG", "X1+rU+EOe0NKw/oohiYxQ4ojm6DUquZdiy5pASZO");
    }



}
