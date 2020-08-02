package com.aml.spring_boot_api.sample_api;

public class BaseResponse {
	private boolean status;
	private String result;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	
}
