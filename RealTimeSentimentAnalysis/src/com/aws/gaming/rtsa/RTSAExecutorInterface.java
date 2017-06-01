package com.aws.gaming.rtsa;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.aws.gaming.rtsa.data.RTSADataPoint;

/**
 * RTSAExecutorInterface.java
 * 
 * An Interface that defines the execution of a facial analysis algorithm. Currently uses Rekognition
 * but can be extended to use other libraries like OpenCV et al. 
 * 
 * @author dhruv
 *
 */
public interface RTSAExecutorInterface {
	
	/**
	 * The main function that executes the facial analysis algorithm. This is an async function that runs
	 * multiple instances of the algorithm on individual frames using Futures to parallelize execution
	 * and get faster results.
	 * 
	 * @param framesList: An ArrayList of image frames that the analysis will run on.
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<RTSADataPoint> runFacialAnalysis(List<Object> framesList) throws InterruptedException, ExecutionException;

}
