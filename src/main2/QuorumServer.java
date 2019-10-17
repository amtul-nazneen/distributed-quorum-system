package main2;

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
		Utils.printServerStart(id);

		ServerSocket ss = new ServerSocket(Constants.SERVER_PORT);
		QuorumMutexImpl s1 = new QuorumMutexImpl();
		while (true) {
			Socket s = null;
			try {
				s = ss.accept();
				String clientHost = s.getInetAddress().getHostName();
				Utils.log("New Connection --****---" + Utils.getClientFromHost(clientHost));
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				s1.updateClientDosMap(dos, Utils.getClientIDFromHost(clientHost));
				Thread t = new QuorumRequestHandler(s, dis, dos, s1);
				t.start();

			} catch (Exception e) {
				s.close();
				e.printStackTrace();
			}
		}
	}
}
