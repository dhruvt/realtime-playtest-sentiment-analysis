package com.aws.gaming.rtsa;

import java.util.List;

import com.amazonaws.services.rekognition.model.Emotion;

public class RTSACalculator {
	
	public Float calculateSentinementFromEmotions(List<Emotion> emotions){
		
		Float sentiment = new Float(0.00);
		
		for(Emotion emotion:emotions){
			switch(emotion.getType()){
			case "HAPPY": 
				sentiment+=emotion.getConfidence();
				break;
			case "SAD":
				sentiment-=emotion.getConfidence();
				break;
			case "ANGRY":
				sentiment-=emotion.getConfidence();
				break;
			case "CONFUSED":
				sentiment-=emotion.getConfidence();
				break;
			case "SURPRISED":
				sentiment+=emotion.getConfidence();
				break;
			case "CALM":
				sentiment+=emotion.getConfidence();
				break;
			case "DISGUSTED":
				sentiment-=emotion.getConfidence();
				break;
			default:
				System.out.println("Unrecognized EMOTION primitive, SKIPPING");
			
			}			
			
		}
			
		return sentiment;
	}

}
