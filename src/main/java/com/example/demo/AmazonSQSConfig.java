package com.example.demo;

import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.support.converter.SqsMessageConversionContext;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AmazonSQSConfig {
//    @Value("${cloud.aws.region.static}")
    private String region;

//    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

//    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;


    @Bean
    public SqsAsyncClient sqsAsyncClient(){
        return  SqsAsyncClient.builder()
                .credentialsProvider
                        (StaticCredentialsProvider.create
                                (AwsBasicCredentials.create
                                        (awsAccessKey, awsSecretKey)))
                .region(Region.of(region)).build();
    }

    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListnerContainerFactory(){
        return SqsMessageListenerContainerFactory
                .builder()
                .sqsAsyncClient(sqsAsyncClient())
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(){
        return SqsTemplate.newTemplate(sqsAsyncClient());
    }
}
