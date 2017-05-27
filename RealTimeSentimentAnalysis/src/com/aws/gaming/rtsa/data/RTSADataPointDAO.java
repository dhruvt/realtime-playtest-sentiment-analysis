package com.aws.gaming.rtsa.data;

import java.util.List;

public interface RTSADataPointDAO {
	
	public void save(RTSADataPoint rtsaDataPoint);
	public List<RTSADataPoint> getRTSADataPointsByTimestampRange(Long beginTimestamp, Long endTimestamp);

}
