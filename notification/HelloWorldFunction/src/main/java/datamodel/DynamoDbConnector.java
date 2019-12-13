package datamodel;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDbConnector {
    static AmazonDynamoDB dynamoDb ;

    public static void init() {
        if (dynamoDb == null) {
            DefaultAWSCredentialsProviderChain credentialsProviderChain=new DefaultAWSCredentialsProviderChain();
            credentialsProviderChain.getCredentials();
            dynamoDb = AmazonDynamoDBClientBuilder
                    .standard()
                    .withCredentials(credentialsProviderChain)
                    .withRegion("us-east-2")
                    .build();
            System.out.println("I created the client");
        }

    }

    public AmazonDynamoDB getClient() {
        return dynamoDb;
    }
}
