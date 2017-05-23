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
import com.aws.gaming.rtsa.data.S3Location;

public class RTSAS3ObjectExecutor implements RTSAExecutorInterface {

	@Override
	public List<RTSADataPoint> runFacialAnalysis(List<Object> framesList)
			throws InterruptedException, ExecutionException {
		@SuppressWarnings("unchecked")
		List<S3Location> fileNames = (List<S3Location>)(Object)framesList;
		int threads = Runtime.getRuntime().availableProcessors();
		ExecutorService service = Executors.newFixedThreadPool(threads);
		
		List<Future<RTSADataPoint>> futures = new ArrayList<Future<RTSADataPoint>>();
		for(final S3Location frameLocation:fileNames){
			Callable<RTSADataPoint> callable = new Callable<RTSADataPoint>(){
				public RTSADataPoint call() throws Exception{
					DetectFaceInterface dfi = new DetectFaceInterfaceImpl();
					List<FaceDetail> faceDetails = dfi.detectFaceFromS3(frameLocation.getBucketName(), frameLocation.getFileName());
					String fileName = frameLocation.getFileName().replaceFirst("[.][^.]+$", "");
					Float sentiment = RTSACalculator.calculateSentinementFromEmotions(faceDetails.get(0).getEmotions());
					return new RTSADataPoint(faceDetails, Long.parseLong(fileName),sentiment);
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
