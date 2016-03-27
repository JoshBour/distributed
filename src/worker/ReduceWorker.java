package worker;

import java.util.Map;

public interface ReduceWorker extends Worker {
	void waitForMasterAck();
	
	Map<Integer, Object> reduce(int key, Object value);
	
	Map<Integer, Object> sendResults();
}
