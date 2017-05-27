package com.aws.gaming;

import com.aws.gaming.rekognition.DetectFaceInterface;
import com.aws.gaming.rekognition.DetectFaceInterfaceImpl;
import com.aws.gaming.rtsa.video.VideoToFramesConvertor;

public class RTSACore {

	public static void main(String[] args) {
		
		VideoToFramesConvertor vfc = new VideoToFramesConvertor();
		vfc.convertVideoToFrames("./input/test_vid.mp4");
		//DetectFaceInterface dfi = new DetectFaceInterfaceImpl();
		//dfi.detectFaceFromS3("rtsa-source", "man-talk-2.jpg");

	}

}
