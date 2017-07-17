package com.aws.gaming.rtsa.data;

import java.nio.ByteBuffer;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.aws.gaming.RTSAConfig;

public class RTSADataPointKinesisDAOImpl implements RTSADataPointDAO {

	@Override
	public void save(RTSADataPoint rtsaDataPoint) {
		
		try{
			String record = rtsaDataPoint.toJson();
			
			AmazonKinesis kinesisClient = AmazonKinesisClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			PutRecordRequest putRecordRequest = new PutRecordRequest();
			putRecordRequest.setStreamName(RTSAConfig.getProperty("kinesisOutputStreamName"));
			putRecordRequest.setData(ByteBuffer.wrap(record.getBytes()));
			putRecordRequest.setPartitionKey(rtsaDataPoint.getTimestamp().toString());
			
			kinesisClient.putRecord(putRecordRequest);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
        
	}
}
