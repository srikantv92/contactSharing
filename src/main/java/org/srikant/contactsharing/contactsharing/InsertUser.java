package org.srikant.contactsharing.contactsharing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

public class InsertUser {
	
	static AmazonDynamoDBClient dynamoDB;
	 
	 //Intial Configuration
	 private static void init() throws Exception {
		 	
	        AWSCredentials credentials = null;
	        try {
	            credentials = new ProfileCredentialsProvider("default").getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    "location (C:\\Users\\Endurance\\.aws\\credentials), and is in valid format.",
	                    e);
	        }
	        dynamoDB = new AmazonDynamoDBClient(credentials);
	        Region singapore = Region.getRegion(Regions.AP_SOUTHEAST_1);
	        dynamoDB.setRegion(singapore);
	    }
	 
	 public String insertUser(NewUser newUser) throws Exception{
		 init();
		 String insertStatus="Status Invalid";
		 try {
			 
			 Map<String, AttributeValue> item = newItem(newUser);
			 PutItemRequest putItemRequest = new PutItemRequest("Person", item);
	         PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
	         insertStatus = "Insertion Success";
		 } catch (AmazonServiceException ase) {
			 insertStatus = insertStatus + "Caught an AmazonServiceException, which means your request made it "
	                    + "to AWS, but was rejected with an error response for some reason.\n";
			 insertStatus = insertStatus + "Error Message:    " + ase.getMessage()+"\n";
			 insertStatus = insertStatus + "HTTP Status Code: " + ase.getStatusCode()+"\n";
			 insertStatus = insertStatus + "AWS Error Code:   " + ase.getErrorCode()+"\n";
			 insertStatus = insertStatus + "Error Type:       " + ase.getErrorType()+"\n";
			 insertStatus = insertStatus + "Request ID:       " + ase.getRequestId()+"\n";
			 System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to AWS, but was rejected with an error response for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
				 insertStatus = insertStatus + "Caught an AmazonClientException, which means the client encountered "
		                    + "a serious internal problem while trying to communicate with AWS, "
		                    + "such as not being able to access the network.\n";	
				 insertStatus = insertStatus +"Error Message: " + ace.getMessage();
	            System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with AWS, "
	                    + "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        }

		 
		 return insertStatus;
	 }

	 private static Map<String, AttributeValue> newItem(NewUser newUser) {
	        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
	        item.put("Id_QrCode", new AttributeValue().withN(newUser.getId_QrCode()));
	        item.put("name", new AttributeValue(newUser.getName()));
	        item.put("email", new AttributeValue().withSS(newUser.getEmail()));
	        item.put("phone", new AttributeValue().withSS(newUser.getPhone()));
	        item.put("country", new AttributeValue(newUser.getCountry()));
	        item.put("image", new AttributeValue(newUser.getImage()));
	        System.out.println(item);
	        return item;
	    }

}
