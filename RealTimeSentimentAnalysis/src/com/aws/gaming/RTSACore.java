package com.aws.gaming;

import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.aws.gaming.rtsa.RTSAListenerFactory;

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
	
    private static AWSCredentialsProvider credentialsProvider;


	public static void main(String[] args) {
		init();
		
		String workerId =  UUID.randomUUID().toString();
        KinesisClientLibConfiguration kinesisClientLibConfiguration =
                new KinesisClientLibConfiguration(RTSAConfig.getProperty("kinesisInputAppName"),
                        RTSAConfig.getProperty("kinesisInputStreamName"),
                        credentialsProvider,
                        workerId);
        kinesisClientLibConfiguration.withInitialPositionInStream(InitialPositionInStream.LATEST);
        kinesisClientLibConfiguration.withRegionName("us-west-2");
        
        IRecordProcessorFactory recordProcessorFactory = new RTSAListenerFactory();
        Worker worker = new Worker(recordProcessorFactory, kinesisClientLibConfiguration);

        System.out.printf("Running %s to process stream %s as worker %s...\n",
        		RTSAConfig.getProperty("kinesisInputAppName"),
        		RTSAConfig.getProperty("kinesisInputStreamName"),
                workerId);

        int exitCode = 0;
        try {
            worker.run();
        } catch (Throwable t) {
            System.err.println("Caught throwable while processing data.");
            t.printStackTrace();
            exitCode = 1;
        }
        System.exit(exitCode);
		
	}
	
	private static void init(){
		
		java.security.Security.setProperty("networkaddress.cache.ttl", "60");
		credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (~/.aws/credentials), and is in valid format.", e);
        }
		
	}		

}
