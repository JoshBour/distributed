package worker;

import java.util.Map;

public interface MapWorker extends Worker {
	Map<Integer, Object> map(Object key, Object value);
	void notifyMaster();
	void sentToReducers(Map<Integer,Object> data);
}
