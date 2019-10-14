package quorum.app.client;

import quorum.app.client.clients.Client1;
import quorum.app.client.clients.Client2;
import quorum.app.client.clients.Client3;
import quorum.app.client.clients.Client4;
import quorum.app.client.clients.Client5;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */
/**
 * Orchestrator class for starting the clients, Pass the client id to start a
 * client
 */
public class Client {

	public static void main(String[] args) {
		if (args.length < 1) {
			Utils.log("No Client ID provided.");
			return;
		}
		String id = args[0];
		if ("1".equals(id)) {
			Client1 client = new Client1();
			Utils.logWithSeparator("Starting Client ID:1");
			try {
				client.startClient1();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("2".equals(id)) {
			Client2 client = new Client2();
			Utils.logWithSeparator("Starting Client ID:2");
			try {
				client.startClient2();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("3".equals(id)) {
			Client3 client = new Client3();
			Utils.logWithSeparator("Starting Client ID:3");
			try {
				client.startClient3();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("4".equals(id)) {
			Client4 client = new Client4();
			Utils.logWithSeparator("Starting Client ID:4");
			try {
				client.startClient4();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("5".equals(id)) {
			Client5 client = new Client5();
			Utils.logWithSeparator("Starting Client ID:5");
			try {
				client.startClient5();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
