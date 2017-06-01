package com.aws.gaming.rtsa.video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.aws.gaming.RTSAConfig;
import com.aws.gaming.rtsa.exception.RTSAVideoConversionException;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;


/**
 * VideoToFramesConvertor.java
 * 
 * Using {@link IMediaReader}, takes a media container, finds the first video stream, decodes that
 * stream, and then writes video frames out to a PNG image file every 5
 * seconds, based on the video presentation timestamps.
 * 
 * Uses Xuggle-Xuggler-Main, a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * @author dhruv
 *
 */
public class VideoToFramesConvertor {

    public static final double SECONDS_BETWEEN_FRAMES = 1;
    
    
    private static int mVideoStreamIndex = -1; // Index used to ensure we display frames from only one video stream from the media container.
    
    private static long mLastPtsWrite = Global.NO_PTS; // Time of last frame write
    
    public static final long MICRO_SECONDS_BETWEEN_FRAMES =
            (long)(Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
    
    private static List<String> frameFilesList = new ArrayList<String>();

	public List<String> convertVideoToFrames(String fileName) throws RTSAVideoConversionException{
		
        try{
			IMediaReader mediaReader = ToolFactory.makeReader(fileName);
	        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
	        mediaReader.addListener(new ImageSnapListener());
	        while (mediaReader.readPacket() == null) ;  // Read out the contents of the media file and dispatch events to the attached listener
	        return frameFilesList;
        }catch(Exception exception){
        	throw new RTSAVideoConversionException(exception.getMessage());
        }
	}
	
    private static class ImageSnapListener extends MediaListenerAdapter {
    	
    	   /** 
    	   * Called after a video frame has been decoded from a media stream.
    	   * Optionally a BufferedImage version of the frame may be passed
    	   * if the calling {@link IMediaReader} instance was configured to
    	   * create BufferedImages.
    	   * 
    	   * This method blocks, so return quickly.
    	   */
    	public void onVideoPicture(IVideoPictureEvent event) {
        	
            // if the selected video stream id is not yet set, select current video stream
            if (event.getStreamIndex() != mVideoStreamIndex) {
                if (mVideoStreamIndex == -1)
                    mVideoStreamIndex = event.getStreamIndex();
        		else
        			return;
        		}
            
            // if uninitialized, back date mLastPtsWrite to get the very first frame
            if (mLastPtsWrite == Global.NO_PTS)
                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;

            // if it's time to write the next frame
            if (event.getTimeStamp() - mLastPtsWrite >=
                    MICRO_SECONDS_BETWEEN_FRAMES) {
                String outputFilename = dumpImageToFile(event.getImage());
                
                double seconds = ((double) event.getTimeStamp()) /
                        Global.DEFAULT_PTS_PER_SECOND;
                System.out.printf(
                        "Decoded Frame at elapsed time of %6.3f seconds wrote: %s\n",
                        seconds, outputFilename);
                frameFilesList.add(outputFilename);
                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;

            }

        	
    	}
        
        private String dumpImageToFile(BufferedImage image) {
        	try{
                String outputFilename = RTSAConfig.getProperty("framesLocation") + System.currentTimeMillis() + ".png";
                ImageIO.write(image, "png", new File(outputFilename));
                return outputFilename;
        	}catch (IOException e) {
                e.printStackTrace();
                return null;
        	}

        }
    	
    }

}
