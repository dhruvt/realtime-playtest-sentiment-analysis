package com.aws.gaming.rekognition;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.util.IOUtils;


/**
 * DetectFaceInterfaceImpl.java
 * 
 * Implementation of the DetectFaceInterface, provide two methods
 * detectFaceFromS3: This will detect images stored within S3
 * detectFaceFromLocalFile: This will detect images stored within the local file system
 * 
 * Images supported for facial detection should either be in .png or .jpg format
 * 
 * @author dhruv
 *
 */
public class DetectFaceInterfaceImpl implements DetectFaceInterface {
	
	private AWSCredentials credentials;
	
	public DetectFaceInterfaceImpl() throws AmazonClientException{
		try{
			ProfileCredentialsProvider pcp = new ProfileCredentialsProvider("default");
			credentials = pcp.getCredentials();			
		}catch (Exception e){
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
		            + "Please make sure that your credentials file is at the correct "
		            + "location (/Users/userid.aws/credentials), and is in a valid format.", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.aws.gaming.rekognition.DetectFaceInterface#detectFaceFromS3(java.lang.String, java.lang.String)
	 */
	@Override
	public List<FaceDetail> detectFaceFromS3(String bucket, String file) throws AmazonRekognitionException{
		AmazonRekognition arc =  AmazonRekognitionClientBuilder.standard()
								.withRegion(Regions.US_WEST_2)
								.withCredentials(new AWSStaticCredentialsProvider(credentials))
								.build();
		DetectFacesRequest dfr = new DetectFacesRequest()
								.withImage(new Image().withS3Object(new S3Object().withName(file).withBucket(bucket)))
								.withAttributes(Attribute.ALL);
		try{
			DetectFacesResult result = arc.detectFaces(dfr);
			List<FaceDetail> fds = result.getFaceDetails();
			return fds;
		}catch (AmazonRekognitionException are){
			throw new AmazonRekognitionException(are.toString());
		}
		
	}

	/* (non-Javadoc)
	 * @see com.aws.gaming.rekognition.DetectFaceInterface#detectFaceFromLocalFile(java.lang.String)
	 */
	@Override
	public List<FaceDetail> detectFaceFromLocalFile(String filePath) {
		try{
			ByteBuffer imageBuffer;
			InputStream is = new FileInputStream(new File(filePath));
			imageBuffer = ByteBuffer.wrap(IOUtils.toByteArray(is));
			is.close();
			
			AmazonRekognition arc =  AmazonRekognitionClientBuilder.standard()
					.withRegion(Regions.US_WEST_2)
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.build();
			
			DetectFacesRequest dfr = new DetectFacesRequest().withImage(new Image().withBytes(imageBuffer))
									.withAttributes(Attribute.ALL);
			DetectFacesResult result = arc.detectFaces(dfr);
			List<FaceDetail> fds = result.getFaceDetails();
			return fds;
			
		}catch (Exception e){
			throw new AmazonRekognitionException(e.toString());
		}
		
	}

}
