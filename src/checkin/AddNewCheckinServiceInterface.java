package checkin;

interface AddNewCheckinServiceInterface {
	
	public void initialize();
	public void waitForNewCheckinsThread();
	public void insertCheckingToDatabase(Object x);
	public void askToClient();
	
}