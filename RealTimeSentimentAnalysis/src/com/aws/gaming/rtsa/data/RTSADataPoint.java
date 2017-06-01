package com.aws.gaming.rtsa.data;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.rekognition.model.FaceDetail;

@DynamoDBTable(tableName="RTSADataPoint")
public class RTSADataPoint implements Comparable<RTSADataPoint>{
	
	public Long timestamp;
	public List<FaceDetail> faceDetails;
	public Float sentimentValue;
	public String emotionDetailsString;
	
	public RTSADataPoint(List<FaceDetail> faceDetails, Float sentimentValue){
		this.faceDetails=faceDetails;
		this.timestamp = new Long(System.currentTimeMillis());
		this.sentimentValue = sentimentValue;
		this.emotionDetailsString = faceDetails.get(0).getEmotions().toString();
	}
	
	public RTSADataPoint(List<FaceDetail> faceDetails, Long timestamp, Float sentimentValue){
		this.faceDetails=faceDetails;
		this.timestamp = timestamp;
		this.sentimentValue = sentimentValue;
		this.emotionDetailsString = faceDetails.get(0).getEmotions().toString();
	}

	@DynamoDBHashKey(attributeName="Timestamp")
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@DynamoDBIgnore
	public List<FaceDetail> getFaceDetails() {
		return faceDetails;
	}

	public void setFaceDetails(List<FaceDetail> faceDetails) {
		this.faceDetails = faceDetails;
	}
		
	@DynamoDBAttribute(attributeName="Sentiment")
	public Float getSentimentValue() {
		return sentimentValue;
	}

	public void setSentimentValue(Float sentimentValue) {
		this.sentimentValue = sentimentValue;
	}
	
	
	@DynamoDBAttribute(attributeName="EmotionDetails")
	public String getFaceDetailsString() {
		return emotionDetailsString;
	}

	public void setFaceDetailsString(String faceDetailsString) {
		this.emotionDetailsString = faceDetailsString;
	}

	@Override
	public int compareTo(RTSADataPoint arg0) {
		return this.timestamp.compareTo(arg0.getTimestamp());
	}
	
	

}
