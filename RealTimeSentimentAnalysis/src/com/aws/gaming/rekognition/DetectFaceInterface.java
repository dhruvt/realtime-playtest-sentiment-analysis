package com.aws.gaming.rekognition;

import java.util.List;

import com.amazonaws.services.rekognition.model.FaceDetail;

public interface DetectFaceInterface {
	
	public List<FaceDetail> detectFaceFromS3(String bucket, String file);
	public List<FaceDetail> detectFaceFromLocalFile(String filePath);

}
