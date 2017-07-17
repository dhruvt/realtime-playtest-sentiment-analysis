package com.aws.gaming.rtsa.data;

public class S3Location {
	
	public String bucketName;
	public String fileName;
	
	public S3Location(String bucketName, String fileName) {
		this.bucketName = bucketName;
		this.fileName = fileName;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
