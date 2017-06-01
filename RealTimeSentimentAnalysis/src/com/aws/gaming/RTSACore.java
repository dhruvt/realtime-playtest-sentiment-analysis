package com.aws.gaming;

import java.util.ArrayList;
import java.util.List;

import com.aws.gaming.rtsa.RTSAExecutorInterface;
import com.aws.gaming.rtsa.RTSALocalFileExecutor;
import com.aws.gaming.rtsa.data.RTSADataPoint;
import com.aws.gaming.rtsa.data.RTSADataPointDAO;
import com.aws.gaming.rtsa.data.RTSADataPointDynamoDBDAOImpl;
import com.aws.gaming.rtsa.data.RTSADataPointKinesisDAOImpl;
import com.aws.gaming.rtsa.video.VideoToFramesConvertor;

public class RTSACore {

	public static void main(String[] args) {
		
		VideoToFramesConvertor vfc = new VideoToFramesConvertor();
		List<String> filesLists = vfc.convertVideoToFrames("./input/test_vid.mp4");
		List<Object> objectList = new ArrayList<Object>(filesLists);
		List<RTSADataPoint> rtsaDataPoints = new ArrayList<RTSADataPoint>();
		
		RTSAExecutorInterface rtsaEI = new RTSALocalFileExecutor();
		try{
			rtsaDataPoints = rtsaEI.runFacialAnalysis(objectList);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		RTSADataPointDAO rtsaDataPointDDBDAO = new RTSADataPointDynamoDBDAOImpl();
		RTSADataPointDAO rtsaDataPointKinesisDAO = new RTSADataPointKinesisDAOImpl();
		
		for(RTSADataPoint rtsaDataPoint : rtsaDataPoints){
			System.out.println("TimeStamp:" + rtsaDataPoint.getTimestamp() + "   Sentiment:" + rtsaDataPoint.getSentimentValue());
			rtsaDataPointDDBDAO.save(rtsaDataPoint);
			rtsaDataPointKinesisDAO.save(rtsaDataPoint);
		}
	}

}
