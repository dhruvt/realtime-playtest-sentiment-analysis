package com.aws.gaming.rekognition;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.rekognition.model.FaceDetail;

public class DetectFaceInterfaceImpl implements DetectFaceInterface {
	
	private AWSCredentials credentials;
	
	public DetectFaceInterfaceImpl() throws AmazonClientException{
		try{
			ProfileCredentialsProvider pcp = new ProfileCredentialsProvider("default");
			credentials = pcp.getCredentials();
		}catch (Exception e){
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
		            + "Please make sure that your credentials file is at the correct "
		            + "location (/Users/userid.aws/credentials), and is in a valid format.", e);
		}
	}

	@Override
	public List<FaceDetail> detectFaceFromS3(String bucket, String file) {
		
		return null;
	}

	@Override
	public List<FaceDetail> detectFaceFromLocalFile(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}

}
