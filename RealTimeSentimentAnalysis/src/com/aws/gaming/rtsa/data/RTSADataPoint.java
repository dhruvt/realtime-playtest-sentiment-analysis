package com.aws.gaming.rtsa.data;

import java.util.List;

import com.amazonaws.services.rekognition.model.FaceDetail;

public class RTSADataPoint implements Comparable<RTSADataPoint>{
	
	public Long timestamp;
	public List<FaceDetail> faceDetails;
	public Float sentimentValue;
	
	public RTSADataPoint(List<FaceDetail> faceDetails, Float sentimentValue){
		this.faceDetails=faceDetails;
		this.timestamp = new Long(System.currentTimeMillis());
		this.sentimentValue = sentimentValue;
	}
	
	public RTSADataPoint(List<FaceDetail> faceDetails, Long timestamp, Float sentimentValue){
		this.faceDetails=faceDetails;
		this.timestamp = timestamp;
		this.sentimentValue = sentimentValue;
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
		
	public Float getSentimentValue() {
		return sentimentValue;
	}

	public void setSentimentValue(Float sentimentValue) {
		this.sentimentValue = sentimentValue;
	}

	@Override
	public int compareTo(RTSADataPoint arg0) {
		return this.timestamp.compareTo(arg0.getTimestamp());
	}
	
	

}
