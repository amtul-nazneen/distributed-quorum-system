package old.quorum.app.server.quorumServers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author amtul.nazneen
 */
/**
 * Thread Class that handles incoming and outgoing messages from the connected
 * clients
 */
public class QuorumServerHandler implements Runnable {
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	String serverFolder;
	int clientId;

	/**
	 * Constructor for creating a handler for communication between the server and
	 * the client. Each connected client is run as a separate thread
	 * 
	 * @param socket
	 * @param serverName
	 * @param clientId
	 */
	public QuorumServerHandler(Socket socket, String serverName, int clientId) {
		super();
		this.socket = socket;
		this.serverFolder = serverName;
		this.clientId = clientId;
		try {
			InputStreamReader iReader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(iReader);
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Thread run method that keeps checking for incoming messages from the
	 * connected client and sends reply once the task of read/write/enquire is done
	 */
	@Override
	public void run() {
		String message;
		String sCurrentLine;
		BufferedReader filereader;
		System.out.println("Quorum thread for client is running for:" + socket.getInetAddress());
		try {
			while ((message = reader.readLine()) != null) {
				System.out.println("Recieved from client:" + message);
				writer.println("hello client!");
				System.out.println("Wrote back to client");
				/*
				 * String tokens[] = message.split(","); String operation = tokens[0];
				 * 
				 * if (operation.equalsIgnoreCase(Constants.READ)) { String file = tokens[1];
				 * Utils.log("Received from Process:" + clientId + " -- Operation:" +
				 * operation.toUpperCase() + " ,File:" + file); String lastLine = ""; String
				 * accessFile = Constants.FOLDER_PATH + serverFolder + "/" + file; filereader =
				 * new BufferedReader(new FileReader(accessFile)); while ((sCurrentLine =
				 * filereader.readLine()) != null) { lastLine = sCurrentLine; }
				 * Utils.log("Sending Lastline of " + file + " to Process:" + clientId);
				 * writer.println("Lastline of " + file + " is: " + lastLine); } else if
				 * (operation.equalsIgnoreCase(Constants.WRITE)) { String file = tokens[1];
				 * Utils.log("Received from Process:" + clientId + " -- Operation:" +
				 * operation.toUpperCase() + " File:" + file); String accessFile =
				 * Constants.FOLDER_PATH + serverFolder + "/" + file; String clientdata =
				 * tokens[2]; Utils.log("Data to write in " + file + " from Process:" + clientId
				 * + "-- " + clientdata); File f = new File(accessFile); FileWriter fw = new
				 * FileWriter(f, true); BufferedWriter filewriter = new BufferedWriter(fw);
				 * filewriter.write(clientdata + Constants.EOL); filewriter.close(); fw.close();
				 * Utils.log("For Process:" + clientId + ", finished Writing to File:" + file);
				 * writer.println("Finished writing to " + file + " :-->" + "{ " + clientdata +
				 * "} "); } else if (operation.equalsIgnoreCase(Constants.ENQUIRE)) { String
				 * processnum = tokens[1]; Utils.log("Received ENQUIRE from Process:" +
				 * processnum); String files = null; File folder = new File(serverFolder);
				 * File[] listOfFiles = folder.listFiles(); for (int i = 0; i <
				 * listOfFiles.length; i++) { if (listOfFiles[i].isFile()) { if (i == 0) { files
				 * = listOfFiles[i].getName(); } else { files = files + "," +
				 * listOfFiles[i].getName(); } } }
				 * Utils.log("Sending ENQUIRE results to Process:" + processnum);
				 * writer.println(files); }
				 */

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
