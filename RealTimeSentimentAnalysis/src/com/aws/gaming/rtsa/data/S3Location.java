package com.aws.gaming.rtsa.data;

public class S3Location {
	
	public String bucketName;
	public String fileName;
	public String fileType;
	
	public S3Location(String bucketName, String fileName, String fileType) {
		this.bucketName = bucketName;
		this.fileName = fileName;
		this.fileType = fileType;
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}
