package com.av.backendgame.dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class BaseDynamoDB {

    public BasicAWSCredentials awsCreds;
    public AmazonDynamoDB client;
    public DynamoDB dynamoDB;
    protected String accessKey;
    protected String secretKey;

    private BaseDynamoDB() {
        accessKey = "AKIA2T6PUXVZXB3WP5XY";
        secretKey ="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";
        awsCreds = new BasicAWSCredentials(accessKey,secretKey);
        client=null;


        if(client!=null)
            client.shutdown();
        client = AmazonDynamoDBClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.AP_SOUTHEAST_1).build();
        dynamoDB = new DynamoDB(client);
    }



    private static BaseDynamoDB instance;
    public static final BaseDynamoDB getInstance() {
        return instance == null?instance=new BaseDynamoDB(): instance;
    }
}
