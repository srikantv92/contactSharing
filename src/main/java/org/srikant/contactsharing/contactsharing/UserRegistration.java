package org.srikant.contactsharing.contactsharing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;


@Path("/register")
public class UserRegistration {

    private InsertUser insert =  new InsertUser();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String newUser(NewUser newUser) throws Exception {
    	String insertionStatus = insert.insertUser(newUser);
    	return insertionStatus;	
    }
    
   
    
}
