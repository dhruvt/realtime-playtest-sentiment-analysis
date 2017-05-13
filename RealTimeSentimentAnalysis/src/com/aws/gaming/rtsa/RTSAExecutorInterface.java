package com.aws.gaming.rtsa;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.aws.gaming.rtsa.data.RTSADataPoint;

public interface RTSAExecutorInterface {
	
	 List<RTSADataPoint> runFacialAnalysis(List<Object> framesList) throws InterruptedException, ExecutionException;

}
