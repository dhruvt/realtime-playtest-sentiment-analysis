package com.aws.gaming.rtsa.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.ObjectMapper;

@DynamoDBTable(tableName="RTSADataPoint")
public class RTSADataPoint implements Comparable<RTSADataPoint>{
	
	public Long timestamp;
	public String emotion;
	public Float confidence;
	
	
	public RTSADataPoint(String emotion, Float confidence){
		this.emotion=emotion;
		this.timestamp = new Long(System.currentTimeMillis());
		this.confidence = confidence;		
	}
	
	public RTSADataPoint(String emotion, Long timestamp, Float confidence){
		this.emotion=emotion;
		this.timestamp = timestamp;
		this.confidence = confidence;		
	}

	@DynamoDBHashKey(attributeName="Timestamp")
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
		
	@DynamoDBAttribute(attributeName="Confidence")
	public Float getConfidence() {
		return confidence;
	}

	public void setConfidence(Float confidence) {
		this.confidence = confidence;
	}
	
	
	@DynamoDBAttribute(attributeName="Emotion")
	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	@Override
	public int compareTo(RTSADataPoint arg0) {
		return this.timestamp.compareTo(arg0.getTimestamp());
	}
	
	public String toJson(){
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.writeValueAsString(this);
		}catch(Exception e){			
			return null;
		}
	}
	

}
