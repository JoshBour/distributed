package worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import checkin.Checkin;
import checkin.POI;

public class Reducer extends Thread {
	int port;
	ServerSocket providerSocket;
	Socket clientSocket = null;
	PrintWriter out = null;
	String line;
	ObjectInputStream in = null;

	/**
	 * 
	 * @param The reducer's port port
	 */
	public Reducer(int port) {
		this.port = port;
	}

	public void run() {
		try {
			providerSocket = new ServerSocket(port);
			List<Map<String, List<Checkin>>> results = new ArrayList<Map<String, List<Checkin>>>();
			System.out.println("Reducer listening to port: " + port);

			while (true) {
				clientSocket = providerSocket.accept();
				in = new ObjectInputStream(clientSocket.getInputStream());

				results.add((Map<String, List<Checkin>>) in.readObject());

				Map<POI, List<Checkin>> allMaps = new HashMap<POI, List<Checkin>>();

				if (results.size() == 2) {
					Map<String, List<Checkin>> finalResults = results.get(0);
					for (int i = 1; i < 2; i++) {
						for (Map.Entry<String, List<Checkin>> entry : results.get(i).entrySet()) {
							String key = entry.getKey();
							if (finalResults.containsKey(key)) {

								for (Checkin checkin : entry.getValue()) {
									if (!finalResults.values().contains(checkin)) {
										finalResults.get(key).add(checkin);
									}
								}
							} else {
								finalResults.put(key, entry.getValue());
							}
						}
					}
					
					finalResults.entrySet().parallelStream()
			        .sorted(Map.Entry.<String, List<Checkin>>comparingByValue(new Comparator<List<Checkin>>() {

						@Override
						public int compare(List<Checkin> o1, List<Checkin> o2) {
							return Integer.compare(o1.size(), o2.size());
						}
					}).reversed()).forEach(p -> System.out.println(p.getKey() + " : " + p.getValue().size()));
					
				}
			}

		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		(new Reducer(Integer.parseInt(args[0]))).start();
	}

}
