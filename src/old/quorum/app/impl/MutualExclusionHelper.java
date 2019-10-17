package old.quorum.app.impl;

import java.io.PrintWriter;
import java.sql.Timestamp;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Helper methods to the MutualExclusionImpl class
 */
public class MutualExclusionHelper {

	/**
	 * Method to send a request to another client
	 * 
	 * @param ownerTimestamp
	 * @param ownerProcessnum
	 * @param receivingProcessNum
	 * @param fileName
	 * @param writer
	 */
	public static void sendRequestToProcess(Timestamp ownerTimestamp, int ownerProcessnum, int receivingProcessNum,
			String fileName, PrintWriter[] writer) {
		Utils.log("-->Sending  REQUEST to Process:" + receivingProcessNum + " ,Timestamp:" + ownerTimestamp + " ,File:"
				+ fileName);
		int x = mapProcessNumToWriterIndex(ownerProcessnum, receivingProcessNum);
		writer[x].println(Constants.REQUEST + "," + ownerTimestamp + "," + ownerProcessnum + "," + fileName);
	}

	/**
	 * Method to send a reply to another client
	 * 
	 * @param receivingProcessNum
	 * @param writer
	 * @param ownerProcessnum
	 */
	public static void sendReplyToProcess(int receivingProcessNum, PrintWriter[] writer, int ownerProcessnum) {
		Utils.log("-->Sending  REPLY to Process:" + receivingProcessNum);
		int x = mapProcessNumToWriterIndex(ownerProcessnum, receivingProcessNum);
		// writer[x].println(Constants.REPLY + "," + receivingProcessNum);
	}

	/**
	 * Method to help evaluate the defer condition. If the owning client has already
	 * requested for CS access and the sending client requests for the same
	 * resource, then request is either replied or deferred based on timestamp and
	 * process number. If the resource is different, its replied.
	 * 
	 * @param requestedCSFlag
	 * @param senderTimestamp
	 * @param ownerTimestamp
	 * @param senderProcessNum
	 * @param ownerProcessNum
	 * @param senderFileName
	 * @param myFileName
	 * @return
	 */
	public static boolean evaluateDeferCondition(boolean requestedCSFlag, Timestamp senderTimestamp,
			Timestamp ownerTimestamp, int senderProcessNum, int ownerProcessNum, String senderFileName,
			String myFileName) {
		boolean defer = false;
		int comparisionOutcome = Utils.compareTimestamp(senderTimestamp, ownerTimestamp, true);
		boolean sameFile = checkFileSame(myFileName, senderFileName);
		if (requestedCSFlag && sameFile) {
			if (comparisionOutcome == 1) {
				defer = true;
			} else if (comparisionOutcome == 0) {
				if (senderProcessNum > ownerProcessNum) {
					defer = true;
				}
			}
		}
		return defer;
	}

	/**
	 * Generic Utility method to mapp the process number to the index in writer
	 * array
	 * 
	 * @param ownerProcess
	 * @param receivingProcessNum
	 * @return
	 */
	private static int mapProcessNumToWriterIndex(int ownerProcess, int receivingProcessNum) {
		if (receivingProcessNum > ownerProcess)
			return receivingProcessNum - 2;
		else
			return receivingProcessNum - 1;

	}

	/**
	 * Utility method to check if same resource is being requested
	 * 
	 * @param ownerFileName
	 * @param senderFileName
	 * @return
	 */
	private static boolean checkFileSame(String ownerFileName, String senderFileName) {
		boolean result = false;
		if (ownerFileName != null && !ownerFileName.isEmpty()) {
			if (ownerFileName.equalsIgnoreCase(senderFileName)) {
				result = true;
			}
		}
		return result;

	}

	/**
	 * Tester method
	 * 
	 * @param mutexImpl
	 * @param w1
	 * @param w2
	 * @param w3
	 * @param w4
	 */
	@SuppressWarnings("unused")
	public static void assignChannelWriters(MutualExclusionImpl mutexImpl, PrintWriter w1, PrintWriter w2,
			PrintWriter w3, PrintWriter w4) {
		/*
		 * PrintWriter pw[] = mutexImpl.getWriterForChannel(); if
		 * (Constants.PROCESS_CHANNELS == 1) { pw[0] = w1; } else if
		 * (Constants.PROCESS_CHANNELS == 2) { pw[0] = w1; pw[1] = w2; } else if
		 * (Constants.PROCESS_CHANNELS == 3) { pw[0] = w1; pw[1] = w2; pw[2] = w3; }
		 * else if (Constants.PROCESS_CHANNELS == 4) { pw[0] = w1; pw[1] = w2; pw[2] =
		 * w3; pw[3] = w4; } mutexImpl.setWriterForChannel(pw);
		 */
	}
}
