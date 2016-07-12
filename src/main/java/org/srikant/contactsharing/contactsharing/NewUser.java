package org.srikant.contactsharing.contactsharing;

import java.util.Collection;

public class NewUser {
	
	private String id_QrCode;
	private String name;
	private Collection<String> email;
	private Collection<String> phone;
	private String country;
	private String image="Link to S3 Bucket";
	
	public NewUser(){
		
	}
	
	public String getId_QrCode() {
		return id_QrCode;
	}
	public void setId_QrCode(String id_QrCode) {
		this.id_QrCode = id_QrCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<String> getEmail() {
		return email;
	}
	public void setEmail(Collection<String> email) {
		this.email = email;
	}
	public Collection<String> getPhone() {
		return phone;
	}
	public void setPhone(Collection<String> phone) {
		this.phone = phone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
