package worker;

public interface Worker {
	void initialize();
	
	void waitForTasksThread();
}
