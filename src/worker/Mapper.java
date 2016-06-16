package worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import checkin.Checkin;
import checkin.CheckinCollection;
import checkin.DateRange;
import checkin.LocRectangle;

public class Mapper extends Thread {
	int port, reducerPort;
	String ip;
	ServerSocket providerSocket;
	Socket clientSocket = null;
	PrintWriter out = null;
	String line;
	ObjectInputStream in = null;
	LocRectangle split;
	DateRange dateRange;
	CheckinCollection checkins;

	/**
	 * 
	 * @param The reducer's IP ip
	 * @param The client's port port
	 * @param The reducer's port reducerPort
	 */
	public Mapper(String ip, int port, int reducerPort) {
		this.ip = ip;
		this.port = port;
		this.reducerPort = reducerPort;
	}

	public void run() {
		try {
			providerSocket = new ServerSocket(port);
			System.out.println("Mapper listening to port: " + port);

			while (true) {
				clientSocket = providerSocket.accept();
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new ObjectInputStream(clientSocket.getInputStream());

				split = (LocRectangle) in.readObject();
				dateRange = (DateRange) in.readObject();

				initialize();

				Map<String, List<Checkin>> topResults = checkins.parallelStream()
						.collect(Collectors.groupingBy((c) -> c.getPoi().getPOI()));

				
				topResults.entrySet().stream()
		        .sorted(Map.Entry.<String, List<Checkin>>comparingByValue(new Comparator<List<Checkin>>() {

					@Override
					public int compare(List<Checkin> o1, List<Checkin> o2) {
						return Integer.compare(o1.size(), o2.size());
					}
				}).reversed());

				sentToReducers(topResults);

			}

		} catch (IOException | ClassNotFoundException ioException) {
			ioException.printStackTrace();
		}

	}

	public static void main(String[] args) {
		(new Mapper(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]))).start();
	}

	public void initialize() {
		checkins = CheckinCollection.getCheckinsBySquareAndDate(split, dateRange);
	}

	public void notifyMaster() {
		out.println("finished");
	}

	public void sentToReducers(Map<String, List<Checkin>> topResults) {
		try {
			Socket reducerConnection = new Socket(ip, reducerPort);
			ObjectOutputStream out = new ObjectOutputStream(reducerConnection.getOutputStream());
			out.writeObject(topResults);

			reducerConnection.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
