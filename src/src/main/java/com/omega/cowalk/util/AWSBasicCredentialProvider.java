package com.omega.cowalk.util;


import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public class AWSBasicCredentialProvider implements AwsCredentialsProvider {
    private  final AwsCredentials awsCredentials;

    public AWSBasicCredentialProvider(AwsCredentials awsCredentials)
    {
        this.awsCredentials = awsCredentials;
    }

    @Override
    public AwsCredentials resolveCredentials() {
        return awsCredentials;
    }
}
