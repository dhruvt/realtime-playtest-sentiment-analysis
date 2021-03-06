package com.aws.gaming.rtsa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonaws.services.rekognition.model.FaceDetail;
import com.aws.gaming.rekognition.DetectFaceInterface;
import com.aws.gaming.rekognition.DetectFaceInterfaceImpl;
import com.aws.gaming.rtsa.data.RTSADataPoint;

/**
 * RTSALocalFileExecutor.java
 * 
 * Implementation of the RTSAExecutorInterface Interface, this implementation runs the Amazon Rekognition
 * algorithm on a series of frames that are stored on the local file system. 
 * 
 * @author dhruv
 *
 */
public class RTSALocalFileExecutor implements RTSAExecutorInterface {

	/* (non-Javadoc)
	 * @see com.aws.gaming.rtsa.RTSAExecutorInterface#runFacialAnalysis(java.util.List)
	 */
	@Override
	public List<RTSADataPoint> runFacialAnalysis(List<Object> framesList)
			throws InterruptedException, ExecutionException {
		
		@SuppressWarnings("unchecked")
		List<String> fileNames = (List<String>)(Object)framesList;
		int threads = Runtime.getRuntime().availableProcessors();
		ExecutorService service = Executors.newFixedThreadPool(threads);
		System.out.println("Initializing Facial Analysis.....Please wait as this takes time!");
		List<Future<RTSADataPoint>> futures = new ArrayList<Future<RTSADataPoint>>();
		for(final String frame:fileNames){
			Callable<RTSADataPoint> callable = new Callable<RTSADataPoint>(){
				public RTSADataPoint call() throws Exception{
					DetectFaceInterface dfi = new DetectFaceInterfaceImpl();
					List<FaceDetail> faceDetails = dfi.detectFaceFromLocalFile(frame);
					String fileName = frame.replaceFirst("[.][^.]+$", "");
					fileName = fileName.substring(9);
					RTSACalculator rtsaCalculator = new RTSACalculator();
					Float sentiment = rtsaCalculator.calculateSentinementFromEmotions(faceDetails.get(0).getEmotions());
					String emotion = rtsaCalculator.getPredominantEmotion(faceDetails.get(0).getEmotions());
					return new RTSADataPoint(emotion, Long.parseLong(fileName),sentiment);
					
				}
			};
			futures.add(service.submit(callable));
		}
		
		service.shutdown();
		
		List<RTSADataPoint> datapoints = new ArrayList<RTSADataPoint>();
		for(Future<RTSADataPoint> future: futures){
			datapoints.add(future.get());
		}
		return datapoints;
	}
	
	

}
