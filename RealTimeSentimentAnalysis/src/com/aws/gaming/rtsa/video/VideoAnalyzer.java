package com.aws.gaming.rtsa.video;

import java.util.ArrayList;
import java.util.List;

import com.aws.gaming.RTSAConfig;
import com.aws.gaming.rtsa.RTSAExecutorInterface;
import com.aws.gaming.rtsa.RTSALocalFileExecutor;
import com.aws.gaming.rtsa.data.RTSADataPoint;
import com.aws.gaming.rtsa.data.RTSADataPointDAO;
import com.aws.gaming.rtsa.data.RTSADataPointDynamoDBDAOImpl;
import com.aws.gaming.rtsa.data.RTSADataPointKinesisDAOImpl;
import com.aws.gaming.rtsa.exception.RTSAVideoConversionException;

public class VideoAnalyzer {
	
	public void runVideoFileAnalysis(String sourceVideoLocation){
		
		try{
			VideoToFramesConvertor vfc = new VideoToFramesConvertor();
			List<String> framesFileList = vfc.convertVideoToFrames(sourceVideoLocation);
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
	
	private void saveAnalysisData(List<RTSADataPoint> rtsaDataPoints){
		
		RTSADataPointDAO rtsaDataPointDDBDAO = new RTSADataPointDynamoDBDAOImpl();
		RTSADataPointDAO rtsaDataPointKinesisDAO = new RTSADataPointKinesisDAOImpl();
		
		for(RTSADataPoint rtsaDataPoint : rtsaDataPoints){
			System.out.println("TimeStamp:" + rtsaDataPoint.getTimestamp() + "   Emotion:" + rtsaDataPoint.getEmotion());
			
			if(Boolean.parseBoolean(RTSAConfig.getProperty("useDynamoDB")))
				rtsaDataPointDDBDAO.save(rtsaDataPoint);
			if(Boolean.parseBoolean(RTSAConfig.getProperty("useKinesis")))
				rtsaDataPointKinesisDAO.save(rtsaDataPoint);
		}		
	}

}
