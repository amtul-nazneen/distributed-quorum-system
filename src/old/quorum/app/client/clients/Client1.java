package old.quorum.app.client.clients;

/**
 * @author amtul.nazneen
 */

/**
 * Central class that handlers Client 1 actions
 */
public class Client1 {

	/*
	 * MutualExclusionImpl myMutexImpl; int processnum = 1; int counter = 0;
	 * 
	 * Socket server1 = null, server2 = null, server3 = null; Socket s2, s3, s4, s5;
	 * ServerSocket ss2, ss3, ss4, ss5;
	 * 
	 * PrintWriter w2, w3, w4, w5; BufferedReader r2, r3, r4, r5; PrintWriter
	 * writeToServer1, writeToServer2, writeToServer3; BufferedReader
	 * readFromServer1, readFromServer2, readFromServer3;
	 * 
	 * ArrayList<String> serverFileList; ArrayList<String> serverList;
	 * ArrayList<String> taskList; HashMap<String, PrintWriter> serverToWriter;
	 * HashMap<String, BufferedReader> serverToReader;
	 * 
	 * String SERVER = ""; String FILE = ""; String TASK = "";
	 * 
	 *//**
		 * Started method to initiate Client 1. Connects to Servers 1,2 and 3 and
		 * initialises Channel Readers and Writers. Opens connections for the remaining
		 * 4 Clients and initialises Channel Readers and Writers once connected Creates
		 * a Mutex object that handles the RA Algorithm and optimization Generates 20
		 * Random Requests
		 * 
		 * @throws Exception
		 */
	/*
	 * public void startClient1() throws Exception { Timestamp start =
	 * Utils.getTimestamp(); try { // connectToServer(); // connectToOtherClients();
	 * // createServerIOStream(); // createChannelIOStream(); //
	 * createMutexImplementor(); // startChannelThreads();
	 * System.out.println("trying to write to qs threads...");
	 * writeToServer1.write("hello server1 from client1"); //
	 * writeToServer2.write("hello server2 from client1");
	 * System.out.println("end of trying to write to qs threads..."); // init();
	 * 
	 * while (counter < Constants.CLIENT1_CSLIMIT) { try { setRandomRequestParams();
	 * requestForCSaccess(); counter++; Thread.sleep((long) (Math.random() * 1000));
	 * } catch (Exception e) { Utils.log(e.getMessage()); } }
	 * 
	 * 
	 * } catch (Exception e) { Utils.log(e.getMessage()); } finally { if
	 * (Constants.ENABLE_SOCKET_CLOSE) { while (Utils.checkTimeout(start,
	 * Utils.getTimestamp()) <= Constants.CLIENT_TIMEOUT + 8) { Thread.sleep(30000);
	 * } Utils.log("Closing all sockets"); closeAllSockets(); } } }
	 * 
	 *//**
		 * Starter method for Requesting Critical Section Access
		 * 
		 * @throws Exception
		 */
	/*
	 * public void requestForCSaccess() throws Exception { int attempt = counter +
	 * 1; Timestamp myRequestTime = Utils.getTimestamp();
	 * Utils.log("Begin CS_Access: " + attempt + " Timestamp: " + "[" +
	 * myRequestTime + "]"); myMutexImpl.myCSRequestBegin(myRequestTime, FILE);
	 * executeCriticalSection(processnum, counter); myMutexImpl.myCSRequestEnd();
	 * Utils.log("End CS_Access: " + attempt + " Timestamp: " + "[" +
	 * Utils.getTimestamp() + "]"); }
	 * 
	 *//**
		 * Implements the critical section. Depending on the random request chosen,
		 * either does a read from the server or writes to the server
		 * 
		 * @param processnum
		 * @param counter
		 * @throws Exception
		 */
	/*
	 * private void executeCriticalSection(int processnum, int counter) throws
	 * Exception { int attempt = counter + 1;
	 * Utils.log("===================== Starting  CS_Access: [[[[[[[[[ ---- " +
	 * attempt + " #### <-----> " + FILE + " ---- ]]]]]]]]] =====================");
	 * myMutexImpl.executingCSFlag = true; try { if
	 * (Constants.READ.equalsIgnoreCase(TASK)) readFromServer(); else if
	 * (Constants.WRITE.equalsIgnoreCase(TASK)) writeToAllServers();
	 * Thread.sleep(Constants.CLIENT1_CSEXEC); } catch (InterruptedException e) {
	 * e.printStackTrace(); }
	 * Utils.log("===================== Completed CS_Access: [[[[[[[[[ ---- " +
	 * attempt + " #### <----->" + FILE + " ---- ]]]]]]]]] =====================");
	 * myMutexImpl.executingCSFlag = false; myMutexImpl.finishedCSFlag = true;
	 * 
	 * }
	 * 
	 *//**
		 * Method to read the last line of a file from a server
		 * 
		 * @throws Exception
		 */
	/*
	 * private void readFromServer() throws Exception { PrintWriter writeToServer =
	 * serverToWriter.get(SERVER); BufferedReader readFromServer =
	 * serverToReader.get(SERVER); writeToServer.println(Constants.READ + "," +
	 * FILE); String reply; boolean gotReply = false; while (!gotReply) { reply =
	 * readFromServer.readLine(); if (reply != null) {
	 * Utils.log("Read from server:-->" + "{ " + reply + " } ");
	 * Utils.storeToOutputFile(reply, processnum, Constants.READ, FILE); gotReply =
	 * true; } } }
	 * 
	 *//**
		 * Method to append a line at the end of a file at all the server
		 * 
		 * @throws Exception
		 */
	/*
	 * private void writeToAllServers() throws Exception {
	 * writeToServer1.println(Constants.WRITE + "," + FILE + "," +
	 * Constants.WRITE_MESSAGE + processnum + " at " +
	 * myMutexImpl.getMyRequestTimestamp()); writeToServer2.println(Constants.WRITE
	 * + "," + FILE + "," + Constants.WRITE_MESSAGE + processnum + " at " +
	 * myMutexImpl.getMyRequestTimestamp()); writeToServer3.println(Constants.WRITE
	 * + "," + FILE + "," + Constants.WRITE_MESSAGE + processnum + " at " +
	 * myMutexImpl.getMyRequestTimestamp()); String reply = null; boolean gotReply =
	 * false; while (!gotReply) { reply = readFromServer1.readLine(); if (reply !=
	 * null) { gotReply = true; } } Utils.log("Got reply from Server1:" + reply);
	 * Utils.storeToOutputFile(Constants.WRITE_MESSAGE + processnum + " at " +
	 * myMutexImpl.getMyRequestTimestamp(), processnum, Constants.WRITE, FILE);
	 * 
	 * gotReply = false; while (!gotReply) { reply = readFromServer2.readLine(); if
	 * (reply != null) { gotReply = true; } } Utils.log("Got reply from Server2:" +
	 * reply);
	 * 
	 * gotReply = false; while (!gotReply) { reply = readFromServer3.readLine(); if
	 * (reply != null) { gotReply = true; } } Utils.log("Got reply from Server3:" +
	 * reply); }
	 * 
	 *//**
		 * Method to fetch the list of files from the server
		 * 
		 * @throws Exception
		 */
	/*
	 * private void enquireToServer() throws Exception {
	 * writeToServer1.println(Constants.ENQUIRE + "," + processnum); boolean
	 * gotReply = false; String reply = ""; while (!gotReply) { reply =
	 * readFromServer1.readLine(); if (reply != null) { gotReply = true; } }
	 * Utils.log("Storing ENQUIRE Results"); String temp[] = reply.split(",");
	 * serverFileList = new ArrayList<String>(); for (int i = 0; i < temp.length;
	 * i++) serverFileList.add(temp[i]); Collections.sort(serverFileList); }
	 * 
	 *//**
		 * Method to connect to the servers
		 * 
		 * @throws Exception
		 */
	/*
	 * private void connectToServer() throws Exception { server1 = new
	 * Socket(Constants.QUORUM1_HOST, Constants.SERVER_PORT); // server2 = new
	 * Socket(Constants.SERVER2_HOST, Constants.SERVER_PORT); // server3 = new
	 * Socket(Constants.SERVER3_HOST, Constants.SERVER_PORT); }
	 * 
	 *//**
		 * Method top open sockets for other client connections
		 * 
		 * @throws Exception
		 */
	/*
	 * private void connectToOtherClients() throws Exception { // ss2 = new
	 * ServerSocket(Constants.CLIENT2_PORT); // ss3 = new
	 * ServerSocket(Constants.CLIENT3_PORT); // ss4 = new
	 * ServerSocket(Constants.CLIENT4_PORT); // ss5 = new
	 * ServerSocket(Constants.CLIENT5_PORT);
	 * 
	 * s2 = ss2.accept(); s3 = ss3.accept(); s4 = ss4.accept(); s5 = ss5.accept();
	 * 
	 * }
	 * 
	 *//**
		 * Method to create writers and readers for the servers
		 * 
		 * @throws Exception
		 */
	/*
	 * private void createServerIOStream() throws Exception { writeToServer1 = new
	 * PrintWriter(server1.getOutputStream(), true); readFromServer1 = new
	 * BufferedReader(new InputStreamReader(server1.getInputStream())); //
	 * writeToServer2 = new PrintWriter(server2.getOutputStream(), true); //
	 * readFromServer2 = new BufferedReader(new //
	 * InputStreamReader(server2.getInputStream())); // writeToServer3 = new
	 * PrintWriter(server3.getOutputStream(), true); // readFromServer3 = new
	 * BufferedReader(new // InputStreamReader(server3.getInputStream())); }
	 * 
	 *//**
		 * Method to create writers and readers for the client channels
		 * 
		 * @throws Exception
		 */
	/*
	 * private void createChannelIOStream() throws Exception { w2 = new
	 * PrintWriter(s2.getOutputStream(), true); r2 = new BufferedReader(new
	 * InputStreamReader(s2.getInputStream())); w3 = new
	 * PrintWriter(s3.getOutputStream(), true); r3 = new BufferedReader(new
	 * InputStreamReader(s3.getInputStream())); w4 = new
	 * PrintWriter(s4.getOutputStream(), true); r4 = new BufferedReader(new
	 * InputStreamReader(s4.getInputStream())); w5 = new
	 * PrintWriter(s5.getOutputStream(), true); r5 = new BufferedReader(new
	 * InputStreamReader(s5.getInputStream()));
	 * 
	 * }
	 * 
	 *//**
		 * Method to create the mutex object for Client 1. Assigns the channel writers
		 * created above to send replies to
		 */
	/*
	 * private void createMutexImplementor() { myMutexImpl = new
	 * MutualExclusionImpl(processnum);
	 * MutualExclusionHelper.assignChannelWriters(myMutexImpl, w2, w3, w4, w5);
	 * 
	 * }
	 * 
	 *//**
		 * Method to start and run the channel threads
		 */
	/*
	 * private void startChannelThreads() { QuorumClientHandler css2 = new
	 * QuorumClientHandler(server1, myMutexImpl); // QuorumClientHandler css3 = new
	 * QuorumClientHandler(server2, myMutexImpl); // QuorumClientHandler css4 = new
	 * QuorumClientHandler(s4, myMutexImpl); // QuorumClientHandler css5 = new
	 * QuorumClientHandler(s5, myMutexImpl);
	 * 
	 * Thread t2 = new Thread(css2); // Thread t3 = new Thread(css3); // Thread t4 =
	 * new Thread(css4); // Thread t5 = new Thread(css5);
	 * Utils.log("Starting threads to server1 and server2"); t2.start(); //
	 * t3.start(); // t4.start(); // t5.start();
	 * 
	 * }
	 * 
	 *//**
		 * Initialise the server file list from ENQUIRE method's response , task
		 * operations, server names to generate randaom requests
		 * 
		 * @throws Exception
		 */
	/*
	 * private void init() throws Exception { serverList = new ArrayList<String>();
	 * serverList.add(Constants.SERVER_1); serverList.add(Constants.SERVER_2);
	 * serverList.add(Constants.SERVER_3);
	 * 
	 * taskList = new ArrayList<String>(); taskList.add(Constants.WRITE);
	 * taskList.add(Constants.READ);
	 * 
	 * enquireToServer();
	 * 
	 * serverToWriter = new HashMap<String, PrintWriter>();
	 * serverToWriter.put(Constants.SERVER_1, writeToServer1);
	 * serverToWriter.put(Constants.SERVER_2, writeToServer2);
	 * serverToWriter.put(Constants.SERVER_3, writeToServer3);
	 * 
	 * serverToReader = new HashMap<String, BufferedReader>();
	 * serverToReader.put(Constants.SERVER_1, readFromServer1);
	 * serverToReader.put(Constants.SERVER_2, readFromServer2);
	 * serverToReader.put(Constants.SERVER_3, readFromServer3); }
	 * 
	 *//**
		 * Method to generate random requests [file,server and task]
		 */
	/*
	 * private void setRandomRequestParams() { For selecting file int randomInt =
	 * (int) (30.0 * Math.random()); if (randomInt <= 10 && randomInt >= 0) FILE =
	 * serverFileList.get(0); if (randomInt <= 20 && randomInt >= 11) FILE =
	 * serverFileList.get(1); if (randomInt <= 30 && randomInt >= 21) FILE =
	 * serverFileList.get(2); For selecting task randomInt = (int) (20.0 *
	 * Math.random()); if (randomInt <= 10 && randomInt >= 0) TASK =
	 * taskList.get(0); if (randomInt <= 20 && randomInt >= 11) TASK =
	 * taskList.get(1); if (Constants.READ.equalsIgnoreCase(TASK)) { For selecting
	 * server randomInt = (int) (30.0 * Math.random()); if (randomInt <= 10 &&
	 * randomInt >= 0) SERVER = serverList.get(0); if (randomInt <= 20 && randomInt
	 * >= 11) SERVER = serverList.get(1); if (randomInt <= 30 && randomInt >= 21)
	 * SERVER = serverList.get(2); } if (Constants.READ.equalsIgnoreCase(TASK))
	 * Utils.log(" *********>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
	 * + "------->" + (counter + 1) + " Randomly Chosen, " + "TASK:" + TASK + " ," +
	 * Utils.getQuorumServerFromHost(SERVER) + " ,FILE:" + FILE); else
	 * Utils.log(" *********>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
	 * + "------->" + (counter + 1) + " Randomly Chosen, " + "TASK:" + TASK +
	 * " ,FILE:" + FILE); }
	 * 
	 *//**
		 * Closing all sockets after timeout
		 * 
		 * @throws Exception
		 *//*
			 * public void closeAllSockets() { try { server1.close(); server2.close();
			 * server3.close(); s2.close(); s3.close(); s4.close(); s5.close(); ss2.close();
			 * ss3.close(); ss4.close(); ss5.close(); } catch (Exception e) { } }
			 */
}
