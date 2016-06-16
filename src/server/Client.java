package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import checkin.DateRange;
import checkin.LocRectangle;

public class Client extends Thread {
	private static final int MAPPERS_COUNT = 3;

	private String[] ip;
	private int port;
	private int mapperLoopCounter;
	private Socket connection = null;
	private ObjectOutputStream out = null;
	private DataInputStream in = null;
	private LocRectangle userRegion;
	private DateRange dateRange;

	/**
	 * 
	 * @param args
	 */
	public Client(String[] args) {
		if (args.length < 8) {
			System.out.println("Error: Not enough arguments provided.");
			System.exit(0);
		} else {
			// args[0-3] are latX, latY, lnX, lnY in this order
			userRegion = new LocRectangle(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
					Double.parseDouble(args[2]), Double.parseDouble(args[3]));

			// args[4-6] are the mapper ip addresses
			ip = new String[]{args[4],args[5],args[6]};
			
			// args[5] is the first mapper port, eg 1000
			// the other mappers must increment from that, so they would be 1001 and 1002
			port = Integer.parseInt(args[5]);
			
			// args[7-8] are the date ranges
			dateRange = new DateRange(args[6], args[7]);
		}
	}

	public void run() {
		LocRectangle[] splits = userRegion.split(MAPPERS_COUNT);
		ArrayList<Socket> mappers = new ArrayList<Socket>();

		for (mapperLoopCounter = 0; mapperLoopCounter < MAPPERS_COUNT; mapperLoopCounter++) {
			// new Thread() {
			// public void run() {

			try {
				connection = new Socket(ip[mapperLoopCounter], port);
				out = new ObjectOutputStream(connection.getOutputStream());
				in = new DataInputStream(connection.getInputStream());

				out.writeObject(splits[mapperLoopCounter]);
				out.writeObject(dateRange);

				mappers.add(connection);
			} catch (UnknownHostException unknownHost) {
				System.out.println("Unknown host!");
			} catch (IOException ioException) {
				ioException.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
					connection.close();
					port++;
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
			// }
			// }.start();
		}
	}

	public static void main(String[] args) {
		(new Client(args)).start();

	}
}
