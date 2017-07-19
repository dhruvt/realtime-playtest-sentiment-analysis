package com.aws.gaming.rtsa;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.gaming.RTSAConfig;
import com.aws.gaming.rtsa.data.RTSADataPoint;
import com.aws.gaming.rtsa.data.RTSADataPointDAO;
import com.aws.gaming.rtsa.data.RTSADataPointDynamoDBDAOImpl;
import com.aws.gaming.rtsa.data.RTSADataPointKinesisDAOImpl;
import com.aws.gaming.rtsa.data.S3Location;

public class RTSARecordsProcessor implements Runnable {
	
	private static final Log LOG = LogFactory.getLog(RTSARecordsProcessor.class);
	
	private ArrayList<S3Location> s3Locations;
	
	public RTSARecordsProcessor(ArrayList<S3Location> s3Locations){
		this.s3Locations=s3Locations;
	}

	@Override
	public void run() {
		
		List<RTSADataPoint> rtsaDataPoints = new ArrayList<RTSADataPoint>();
	      
        try {
        	RTSAExecutorInterface rtsaExecutor = new RTSAS3ObjectExecutor();
        	List<Object> objectList = new ArrayList<Object>(s3Locations);
			rtsaDataPoints = rtsaExecutor.runFacialAnalysis(objectList);	
			saveAnalysisData(rtsaDataPoints);
			
        } catch (Exception e) {
            LOG.error("Error Processing Kinesis Records in RTSARecordsProcessor " + e);
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
