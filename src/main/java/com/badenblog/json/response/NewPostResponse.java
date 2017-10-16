package com.badenblog.json.response;

public class NewPostResponse {
	String tittle;

	public NewPostResponse(){
	}
	
	public NewPostResponse(final String tittle){
		this.tittle=tittle;
	}
	
	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
}
