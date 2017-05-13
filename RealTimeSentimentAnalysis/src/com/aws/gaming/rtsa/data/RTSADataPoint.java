package com.aws.gaming.rtsa.data;

import java.util.List;

import com.amazonaws.services.rekognition.model.FaceDetail;

public class RTSADataPoint implements Comparable<RTSADataPoint>{
	
	public Long timestamp;
	public List<FaceDetail> faceDetails;
	
	public RTSADataPoint(List<FaceDetail> faceDetails){
		this.faceDetails=faceDetails;
		this.timestamp = new Long(System.currentTimeMillis());
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public List<FaceDetail> getFaceDetails() {
		return faceDetails;
	}

	public void setFaceDetails(List<FaceDetail> faceDetails) {
		this.faceDetails = faceDetails;
	}

	@Override
	public int compareTo(RTSADataPoint arg0) {
		return this.timestamp.compareTo(arg0.getTimestamp());
	}
	
	

}
