package worker;

import java.util.Map;

public interface MapWorkerInterface extends WorkerInterface {
	public Map<Integer, Object> map(Object key, Object data);
	public void notifyMaster();
	public void sentToReducers(Map<Integer,Object> topResults);
}
