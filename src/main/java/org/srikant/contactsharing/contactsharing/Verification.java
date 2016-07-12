package org.srikant.contactsharing.contactsharing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class Verification {

	static AmazonDynamoDBClient dynamoDB;

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

	
	public String verify(VerifyInfo userInfo) throws Exception{
		init();
		
		 try {
			 HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
	            Condition condition = new Condition()
	                .withComparisonOperator(ComparisonOperator.EQ.toString())
	                .withAttributeValueList(new AttributeValue().withN(userInfo.getId_QrCode()));
	            scanFilter.put("Id_QrCode", condition);
	            ScanRequest scanRequest = new ScanRequest("Person").withScanFilter(scanFilter);
	            ScanResult scanResult = dynamoDB.scan(scanRequest);
	            Map<String, AttributeValue> result = scanResult.getItems().get(0);
	            String verifyStatus="";
	            System.out.println(verifyEmail(userInfo, result) + " " + verifyName(userInfo, result) + " " + verifyPhone(userInfo, result) + " " +verifyQrCode(userInfo, result) );
	            if(verifyEmail(userInfo, result) && verifyName(userInfo, result) && verifyPhone(userInfo, result) && verifyQrCode(userInfo, result)){
	            	verifyStatus="Success";
	            	return verifyStatus;
	            }
	            verifyStatus = "Failed";
	            return verifyStatus;

	        } catch (AmazonServiceException ase) {
	            System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to AWS, but was rejected with an error response for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
	            System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with AWS, "
	                    + "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        }		
		return null;		
	}
	
	private boolean verifyName(VerifyInfo userInfo, Map<String, AttributeValue> result){
		String actualName = userInfo.getName().toLowerCase().replace(" ", "");
		String expectedName = result.get("name").getS().toLowerCase().replace(" ", "");
		if(actualName.equals(expectedName)){
			return true;
		}
		return false;
	}
	
	private boolean verifyQrCode(VerifyInfo userInfo, Map<String, AttributeValue> result){
		String actualCode = userInfo.getId_QrCode();
		String expectedCode = result.get("Id_QrCode").getN();
		if(actualCode.equals(expectedCode)){
			return true;
		}
		return false;
	}
	
	private boolean verifyPhone(VerifyInfo userInfo, Map<String, AttributeValue> result){
		String actualPhone = userInfo.getPhone();
		List<String> expectedPhone = result.get("phone").getSS();
		if(expectedPhone.contains(actualPhone)){
			return true;
		}
		return false;
	}
	
	private boolean verifyEmail(VerifyInfo userInfo, Map<String, AttributeValue> result){
		String actualEmail = userInfo.getEmail().trim();
		List<String> expectedEmail = result.get("email").getSS();
		if(expectedEmail.contains(actualEmail)){
			return true;
		}
		
		return false;
	}

}
