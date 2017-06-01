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
		
		String record = "TimeStamp=" + rtsaDataPoint.getTimestamp() + ",Sentiment=" + rtsaDataPoint.getSentimentValue();
		
		AmazonKinesis kinesisClient = AmazonKinesisClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
		PutRecordRequest putRecordRequest = new PutRecordRequest();
		putRecordRequest.setStreamName(RTSAConfig.getProperty("kinesisStreamName"));
		putRecordRequest.setData(ByteBuffer.wrap(record.getBytes()));
		putRecordRequest.setPartitionKey(rtsaDataPoint.getTimestamp().toString());
		
		kinesisClient.putRecord(putRecordRequest);
        //System.out.printf("Successfully put record, partition key : %s, ShardID : %s, SequenceNumber : %s.\n",
        //        putRecordRequest.getPartitionKey(),
        //        putRecordResult.getShardId(),
        //        putRecordResult.getSequenceNumber());
    	
	}
}
