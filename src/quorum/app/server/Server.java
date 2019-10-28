package quorum.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */
/**
 * Class that runs the File Server
 */
public class Server {

	public static void main(String[] args) throws Exception {
		Utils.logWithSeparator("Starting File Server");
		ServerSocket serverSocket = null;
		Socket socket = null;
		ClientRequestCounter clientReqCounter = new ClientRequestCounter();
		try {
			serverSocket = new ServerSocket(Constants.SERVER_PORT);
			while (true) {
				socket = serverSocket.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				ServerRequestHandler connectedClient = new ServerRequestHandler(socket,
						Utils.getClientFromHost(socket.getInetAddress().getHostName()), dis, dos, clientReqCounter);
				Thread clientThread = new Thread(connectedClient);
				clientThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			socket.close();
			serverSocket.close();
		}
	}
}
