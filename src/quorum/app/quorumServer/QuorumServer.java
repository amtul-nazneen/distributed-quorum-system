package quorum.app.quorumServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class QuorumServer {
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			Utils.log("No Quorum-Server ID provided.");
			return;
		}
		String id = args[0];
		Utils.logWithSeparator("Starting Quorum Server:" + id);

		@SuppressWarnings("resource")
		ServerSocket quorumSocket = new ServerSocket(Constants.SERVER_PORT);
		QuorumMutexImpl quorumMutex = new QuorumMutexImpl();
		while (true) {
			Socket socket = null;
			try {
				socket = quorumSocket.accept();
				String clientHost = socket.getInetAddress().getHostName();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				quorumMutex.updateClientDosMap(dos, Utils.getClientIDFromHost(clientHost));
				Thread quorumClientThread = new QuorumRequestHandler(socket, dis, dos, quorumMutex,
						Integer.valueOf(id));
				quorumClientThread.start();

			} catch (Exception e) {
				socket.close();
				e.printStackTrace();
			}
		}
	}
}
