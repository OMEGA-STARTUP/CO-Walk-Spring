package com.omega.cowalk.util;



import com.amazonaws.regions.Regions;


import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@EnableAsync
@Slf4j
public class PictureUploader {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public PictureUploader(AwsCredentials credentials)
    {
        AwsCredentialsProvider awsCredentialsProvider = new AWSBasicCredentialProvider(credentials);
        s3Client = S3Client.builder()
                .credentialsProvider(
                       awsCredentialsProvider
                )
                .region(Region.AP_NORTHEAST_2)
                .build();
    }

    private String getKeyForProfileObject(MultipartFile multipartFile, long userId)
    {
        String[] tempArr = multipartFile.getContentType().split("/");
        String contentType = tempArr[tempArr.length - 1];

        String key = "profile-picture/" + String.valueOf(userId) + "." + contentType;

        return key;
    }


    private String getBucketUrl()
    {
        return String.format("https://%s.s3.ap-northeast-2.amazonaws.com/", bucketName);
    }

    //@Async
    public CompletableFuture<String> uploadProfilePicture(MultipartFile multipartFile, long userId) throws IOException
    {
        String key = getKeyForProfileObject(multipartFile, userId);

        PutObjectResponse putObjectResponse = uploadPicture(key, multipartFile.getInputStream(), multipartFile.getSize());
        log.debug(putObjectResponse.toString());

        CompletableFuture<String> future = new CompletableFuture<String>();
        future.complete(getUrlForProfileObject(multipartFile, userId));

        return future;
    }

    private PutObjectResponse uploadPicture(String key, InputStream inputStream, long fileSize){

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName).acl("public-read").key(key).build();
        return s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, fileSize));

    }

    private String getUrlForProfileObject(MultipartFile multipartFile, long userId)
    {
        return getBucketUrl() + getKeyForProfileObject(multipartFile, userId);
    }



}
