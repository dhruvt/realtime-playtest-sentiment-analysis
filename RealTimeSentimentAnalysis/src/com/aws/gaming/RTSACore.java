package com.aws.gaming;

import java.util.ArrayList;
import java.util.List;

import com.aws.gaming.rtsa.RTSAExecutorInterface;
import com.aws.gaming.rtsa.RTSALocalFileExecutor;
import com.aws.gaming.rtsa.data.RTSADataPoint;
import com.aws.gaming.rtsa.data.RTSADataPointDAO;
import com.aws.gaming.rtsa.data.RTSADataPointDynamoDBDAOImpl;
import com.aws.gaming.rtsa.data.RTSADataPointKinesisDAOImpl;
import com.aws.gaming.rtsa.exception.RTSAVideoConversionException;
import com.aws.gaming.rtsa.video.VideoToFramesConvertor;

/**
 * RTSACore.java
 * 
 * An RTSA sub-system that provides developers of games with raw emotional feedback 
 * from their play-test group in real time. The setup would involve a typical play-test room, 
 * but with a camera feed that captures the video of the players involved in the play-test as it happens. 
 * The camera feed is piped into a system that breaks down the video into individual frames and those 
 * frames are then analyzed for player emotion via Amazon Rekognition. 
 * 
 * The returned EMOTION objects (http://docs.aws.amazon.com/rekognition/latest/dg/API_Emotion.html) are then 
 * aggregated and a real time scale of positive or negative sentiment is created and graphed. 
 * 
 * @author dhruv
 *
 */
public class RTSACore {

	public static void main(String[] args) {
		
		try{
			VideoToFramesConvertor vfc = new VideoToFramesConvertor();
			List<String> framesFileList = vfc.convertVideoToFrames(RTSAConfig.getProperty("sourceVideoLocation"));
			List<Object> objectList = new ArrayList<Object>(framesFileList); //Converting List<String> to List<Object> to comply with Interface declarations
			
			List<RTSADataPoint> rtsaDataPoints = new ArrayList<RTSADataPoint>();
			
			RTSAExecutorInterface rtsaExecutor = new RTSALocalFileExecutor();			
			rtsaDataPoints = rtsaExecutor.runFacialAnalysis(objectList);	
			
			saveAnalysisData(rtsaDataPoints);
			
		}catch(RTSAVideoConversionException rtsaVideoConversionException){
			System.out.println(rtsaVideoConversionException.getMessage());
			System.out.println("Exiting Program");
			System.exit(1);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private static void saveAnalysisData(List<RTSADataPoint> rtsaDataPoints){
		
		RTSADataPointDAO rtsaDataPointDDBDAO = new RTSADataPointDynamoDBDAOImpl();
		RTSADataPointDAO rtsaDataPointKinesisDAO = new RTSADataPointKinesisDAOImpl();
		
		for(RTSADataPoint rtsaDataPoint : rtsaDataPoints){
			System.out.println("TimeStamp:" + rtsaDataPoint.getTimestamp() + "   Sentiment:" + rtsaDataPoint.getSentimentValue());
			
			if(Boolean.parseBoolean(RTSAConfig.getProperty("useDynamoDB")))
				rtsaDataPointDDBDAO.save(rtsaDataPoint);
			if(Boolean.parseBoolean(RTSAConfig.getProperty("useKinesis")))
				rtsaDataPointKinesisDAO.save(rtsaDataPoint);
		}		
	}

}
