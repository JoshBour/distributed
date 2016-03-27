package worker;

import java.util.Map;

public interface ReduceWorkerInterface extends WorkerInterface {
	public void waitForMasterAck();
	
	public Map<Integer, Object> reduce(int key, Object data);
	
	public void sentResults(Map<Integer, Object> map);
}
