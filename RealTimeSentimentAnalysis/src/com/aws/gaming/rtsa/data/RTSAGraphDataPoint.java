package com.aws.gaming.rtsa.data;

public class RTSAGraphDataPoint {
	
	public String actorId;
	public Long timestamp;
	public Long sentiment;
	
	
	public RTSAGraphDataPoint(String actorId, Long timestamp, Long sentiment) {
		super();
		this.actorId = actorId;
		this.timestamp = timestamp;
		this.sentiment = sentiment;
	}
	
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Long getSentiment() {
		return sentiment;
	}
	public void setSentiment(Long sentiment) {
		this.sentiment = sentiment;
	}
	
	

}
