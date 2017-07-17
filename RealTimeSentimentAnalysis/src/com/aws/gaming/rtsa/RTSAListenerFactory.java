package com.aws.gaming.rtsa;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;


public class RTSAListenerFactory implements IRecordProcessorFactory{

	@Override
	public IRecordProcessor createProcessor() {
		return new RTSAListener();
	}

}
