
package com.navigraph.photobook.util;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

class AwsClientFactory {

    static final String LOCAL_IAM = "https://localhost:4593";
    static final String LOCAL_SNS = "https://localhost:4575";
    static final String LOCAL_SQS = "https://localhost:4576";
    static final String LOCAL_S3 = "https://localhost:4572";

    public AWSSimpleSystemsManagement ssm() {
       return AWSSimpleSystemsManagementClientBuilder.defaultClient();
    }

    public AWSKMS kms() {
        return AWSKMSClientBuilder.defaultClient();
    }

    public AwsCrypto crypto() {
        return new AwsCrypto();
    }

    public AmazonS3 s3() {
        if ( Environment.current == Environment.PRODUCTION ) {
          retunr  AmazonS3ClientBuilder.defaultClient();
        } else {
            System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");

           return AmazonS3ClientBuilder
                    .standard()
                    .withEndpointConfiguration(
                            new EndpointConfiguration( LOCAL_S3, 'us-east-1' )
                    )
                    .withPathStyleAccessEnabled(true)
                    .build();
        }
    }

    /**
     * SNS, SQS, and IAM USE LOCALSTACK IN DEV!
     *
     * These all need the same boilerplate, abstracting it into one private builder method for
     * Amazon clients that need to split-brain between Localstack and defaultclient
     */
    private Object buildLocalstackableClient( Class builderClass, String endpoint ) {
        if ( Environment.current == Environment.PRODUCTION ) {
           return builderClass.defaultClient()
        } else {
            System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");

           return builderClass
                    .standard()
                    .withEndpointConfiguration(
                            new EndpointConfiguration( endpoint, 'us-east-1' )
                    )
                    .build();
        }
    }

    public AmazonSNS sns() {
        return buildLocalstackableClient( AmazonSNSClientBuilder, LOCAL_SNS ) as AmazonSNS;
    }

    public AmazonSQS sqs() {
       return buildLocalstackableClient( AmazonSQSClientBuilder, LOCAL_SQS ) as AmazonSQS;
    }

    public AmazonIdentityManagement iam() {
       return buildLocalstackableClient( AmazonIdentityManagementClientBuilder, LOCAL_IAM ) as AmazonIdentityManagement;
    }

    public AWSSecurityTokenService sts() {
       return AWSSecurityTokenServiceClientBuilder.defaultClient();
    }

}
