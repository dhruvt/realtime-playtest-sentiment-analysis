package com.aws.gaming.rekognition;

import java.util.List;

import com.amazonaws.services.rekognition.model.FaceDetail;

/**
 * An Interface that defines the supported methods of doing facial analysis on image files.
 * This can be extended as more mediums are supported later on within the Rekognition API
 * 
 * @author dhruv
 *
 */
public interface DetectFaceInterface {
	
	/**
	 * Runs a Rekognition Facial Analysis on a remote file stored in S3
	 * @param bucket: Bucket where the images files are stored
	 * @param file: Filename to do the analysis on
	 * @return
	 */
	public List<FaceDetail> detectFaceFromS3(String bucket, String file);
	/**
	 * Runs a Rekognition Facial Analysis on a local disk file
	 * @param filePath: Local filepath where the images to be analysed are stored
	 * @return
	 */
	public List<FaceDetail> detectFaceFromLocalFile(String filePath);

}
