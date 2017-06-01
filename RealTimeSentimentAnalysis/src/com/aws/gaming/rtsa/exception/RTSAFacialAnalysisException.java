package com.aws.gaming.rtsa.exception;

public class RTSAFacialAnalysisException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 616676966986809347L;
	
	public RTSAFacialAnalysisException(String exceptionMessage){
		super("System threw an RTSAFacialAnalysisException: " + exceptionMessage);
	}

}
