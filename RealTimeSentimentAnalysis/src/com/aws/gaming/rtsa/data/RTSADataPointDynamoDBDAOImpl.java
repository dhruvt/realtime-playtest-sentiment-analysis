package com.aws.gaming.rtsa.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


public class RTSADataPointDynamoDBDAOImpl implements RTSADataPointDAO {

	@Override
	public void save(RTSADataPoint rtsaDataPoint) {
		
		try{
			AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			DynamoDBMapper ddbMapper = new DynamoDBMapper(ddbClient);
			ddbMapper.save(rtsaDataPoint);
		}catch(Exception e){
			System.out.println("Error Saving RTSADataPoint with Timestamp: "+ rtsaDataPoint.getTimestamp());
		}

	}	
}
