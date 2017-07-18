package com.aws.gaming.rtsa;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.amazonaws.services.rekognition.model.Emotion;

/**
 * RTSACalculator.java
 * 
 * @author dhruv
 *
 */
public class RTSACalculator {
	
	/**
	 * A very rudimentary calculation of overall sentiment based on the returned EMOTION objects. 
	 * This calculation could definitely be made more sophisticated.
	 * 
	 * @param emotions: A list of EMOTION objects (http://docs.aws.amazon.com/rekognition/latest/dg/API_Emotion.html)
	 * @return
	 */
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
	
	public String getPredominantEmotion(List<Emotion> emotions){
		Collections.sort(emotions, new Comparator<Emotion>(){
			public int compare(Emotion e1, Emotion e2){
				if(e1.getConfidence()==e2.getConfidence())
					return 0;
				return e1.getConfidence()<e2.getConfidence()?-1:1;
			}
		});
		
		return emotions.get(0).getType();
	}

}
