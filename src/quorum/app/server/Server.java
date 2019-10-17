package quorum.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

public class Server {

	public static void main(String[] args) throws Exception {

		ServerSocket ss = null;
		Socket s = null;

		try {
			ss = new ServerSocket(Constants.SERVER_PORT);
			while (true) {
				s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				ServerRequestHandler clientThread = new ServerRequestHandler(s,
						Utils.getClientFromHost(s.getInetAddress().getHostName()), dis, dos);
				Thread t = new Thread(clientThread);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			s.close();
			ss.close();
		}
	}
}
