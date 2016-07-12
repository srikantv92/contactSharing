package org.srikant.contactsharing.contactsharing;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/verify")
public class verifyUser {
	
private Verification verify =  new Verification();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String verifyUser(VerifyInfo userInfo) throws Exception {
    	String verificationStatus = verify.verify(userInfo);
    	return verificationStatus;
}
    
}
