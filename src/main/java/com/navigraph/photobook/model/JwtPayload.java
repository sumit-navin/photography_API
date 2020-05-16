package com.navigraph.photobook.model;

public class JwtPayload {

	//issues at
	private String iat;
	//subject (username in our case)
    private String sub;
    //issuer
    private String iss;
    
    
    public String getIat() {
		return iat;
	}
	public void setIat(String iat) {
		this.iat = iat;
	}
	
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getIss() {
		return iss;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
}
