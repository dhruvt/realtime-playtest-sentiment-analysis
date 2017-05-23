package com.aws.gaming;

import com.aws.gaming.rekognition.DetectFaceInterface;
import com.aws.gaming.rekognition.DetectFaceInterfaceImpl;

public class RTSACore {

	public static void main(String[] args) {
		
		DetectFaceInterface dfi = new DetectFaceInterfaceImpl();
		dfi.detectFaceFromS3("rtsa-source", "man-talk-2.jpg");

	}

}
