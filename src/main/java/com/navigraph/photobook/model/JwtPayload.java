package com.navigraph.photobook.model;

public class JwtPayload {

	private String iat;
    private String sub;
    private Long userId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getIss() {
		return iss;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
}
