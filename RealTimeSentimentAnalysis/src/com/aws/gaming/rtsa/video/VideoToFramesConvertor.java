package com.aws.gaming.rtsa.video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;


public class VideoToFramesConvertor {

    public static final double SECONDS_BETWEEN_FRAMES = 1;
    private static int mVideoStreamIndex = -1;
    private static long mLastPtsWrite = Global.NO_PTS;
    public static final long MICRO_SECONDS_BETWEEN_FRAMES =
            (long)(Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
    private static List<String> frameFilesList = new ArrayList<String>();

	public List<String> convertVideoToFrames(String fileName){
		
        IMediaReader mediaReader = ToolFactory.makeReader(fileName);
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new ImageSnapListener());
        while (mediaReader.readPacket() == null) ;
        return frameFilesList;
	}
	
    private static class ImageSnapListener extends MediaListenerAdapter {
    	
        public void onVideoPicture(IVideoPictureEvent event) {
        	
            if (event.getStreamIndex() != mVideoStreamIndex) {
                if (mVideoStreamIndex == -1)
                    mVideoStreamIndex = event.getStreamIndex();
        		else
        			return;
        		}
            
            if (mLastPtsWrite == Global.NO_PTS)
                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;

            if (event.getTimeStamp() - mLastPtsWrite >=
                    MICRO_SECONDS_BETWEEN_FRAMES) {
                String outputFilename = dumpImageToFile(event.getImage());
                
                double seconds = ((double) event.getTimeStamp()) /
                        Global.DEFAULT_PTS_PER_SECOND;
                System.out.printf(
                        "at elapsed time of %6.3f seconds wrote: %s\n",
                        seconds, outputFilename);
                frameFilesList.add(outputFilename);
                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;

            }

        	
    	}
        
        private String dumpImageToFile(BufferedImage image) {
        	try{
                String outputFilename = "./frames/" + System.currentTimeMillis() + ".png";
                ImageIO.write(image, "png", new File(outputFilename));
                return outputFilename;
        	}catch (IOException e) {
                e.printStackTrace();
                return null;
        	}

        }


    	
    }


}
