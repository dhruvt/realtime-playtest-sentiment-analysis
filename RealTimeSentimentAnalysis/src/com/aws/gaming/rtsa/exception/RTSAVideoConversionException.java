package com.aws.gaming.rtsa.exception;

/**
 * RTSAFacialAnalysisException.ajav
 * 
 * Custom exception class for detecting issues in Video to Image frame executions
 * 
 * @author dhruv
 *
 */
public class RTSAVideoConversionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1438118481075382584L;
	
	public RTSAVideoConversionException(String exceptionMessage){
		super("System threw an RTSAVideoConversionException: " + exceptionMessage);
	}

}
