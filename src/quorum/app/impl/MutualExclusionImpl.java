package quorum.app.impl;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import quorum.app.util.Constants;
import quorum.app.util.Utils;

/**
 * @author amtul.nazneen
 */

/**
 * Main class for implementing Ricart-Agrawala Algorithm
 */
public class MutualExclusionImpl {
	private boolean myRequestCSFlag;
	private int myPendingReplyCount;
	private String myFileName;
	private int myProcessNum;
	private Timestamp myRequestTimestamp;
	private PrintWriter[] writerForChannel;
	private ArrayList<DeferredReply> myDeferredReplies;
	private boolean requestagainF1[];
	private boolean requestagainF2[];
	private boolean requestagainF3[];
	private boolean myFirstCSBeginCompletedF1;
	private boolean myFirstCSBeginCompletedF2;
	private boolean myFirstCSBeginCompletedF3;
	private boolean updatingrequestagain;
	public boolean executingCSFlag;
	public boolean finishedCSFlag;

	public MutualExclusionImpl(int processnum) {
		this.myProcessNum = processnum;
		init();
	}

	/**
	 * Initialise helper variables to maintain the state
	 */
	private void init() {
		myPendingReplyCount = Constants.PROCESS_CHANNELS;
		myRequestTimestamp = null;
		writerForChannel = new PrintWriter[Constants.PROCESS_CHANNELS];
		myDeferredReplies = new ArrayList<DeferredReply>();
		myFileName = "";
		requestagainF1 = new boolean[Constants.TOTAL_CLIENTS + 1];
		requestagainF2 = new boolean[Constants.TOTAL_CLIENTS + 1];
		requestagainF3 = new boolean[Constants.TOTAL_CLIENTS + 1];
		myFirstCSBeginCompletedF1 = false;
		myFirstCSBeginCompletedF2 = false;
		myFirstCSBeginCompletedF3 = false;
		updatingrequestagain = false;
		executingCSFlag = false;
		finishedCSFlag = false;
	}

	/**
	 * Method that's called when a client requests for CS If the CS is requested for
	 * the first time on a resource, the request is sent to all clients. If the
	 * client has already accessed critical section once for a particular resource,
	 * then subsequently it sends requests only to the processes to which it replied
	 * during or after critical section [the RC optimization]
	 * 
	 * @param time
	 * @param fileName
	 * @return
	 */
	public boolean myCSRequestBegin(Timestamp time, String fileName) {
		myRequestCSFlag = true;
		myRequestTimestamp = time;
		myFileName = fileName;
		if ((Constants.FILE1_NAME).equalsIgnoreCase(fileName)) {
			if (!myFirstCSBeginCompletedF1) {

				Utils.log("First CS for: " + fileName + ", Sending request to all processes");
				myPendingReplyCount = Constants.PROCESS_CHANNELS;

				int total = Constants.PROCESS_CHANNELS + 1;
				for (int i = 1; i <= total; i++) {
					if (i != myProcessNum) {
						MutualExclusionHelper.sendRequestToProcess(myRequestTimestamp, myProcessNum, i, myFileName,
								writerForChannel);
					}
				}
				myFirstCSBeginCompletedF1 = true;
			} else {
				Utils.log("CS for: " + fileName + " atleast completed once");
				String reqs = "";
				int count = 0;
				for (int i = 1; i <= 5; i++) {
					if (i != myProcessNum && requestagainF1[i]) {
						reqs = reqs + String.valueOf(i) + ", ";
						count++;
					}
				}
				myPendingReplyCount = count;
				if ((reqs != null) && (reqs.length() > 0)) {
					reqs = reqs.substring(0, reqs.length() - 2);
				}
				Utils.log("Optimization, For " + myFileName + " sending Requests only to Process(es): " + reqs);
				Utils.log("Remaining Replies: " + count);
				for (int i = 1; i <= 5; i++) {
					if (i != myProcessNum && requestagainF1[i]) {
						MutualExclusionHelper.sendRequestToProcess(myRequestTimestamp, myProcessNum, i, myFileName,
								writerForChannel);
					}
				}
			}

		} else if ((Constants.FILE2_NAME).equalsIgnoreCase(fileName)) {
			if (!myFirstCSBeginCompletedF2) {

				Utils.log("First CS for: " + fileName + ", Sending request to all processes");
				myPendingReplyCount = Constants.PROCESS_CHANNELS;

				int total = Constants.PROCESS_CHANNELS + 1;
				for (int i = 1; i <= total; i++) {
					if (i != myProcessNum) {
						MutualExclusionHelper.sendRequestToProcess(myRequestTimestamp, myProcessNum, i, myFileName,
								writerForChannel);
					}
				}
				myFirstCSBeginCompletedF2 = true;
			} else {
				Utils.log("CS for: " + fileName + " atleast completed once");
				String reqs = "";
				int count = 0;
				for (int i = 1; i <= 5; i++) {
					if (i != myProcessNum && requestagainF2[i]) {
						reqs = reqs + String.valueOf(i) + ", ";
						count++;
					}
				}
				myPendingReplyCount = count;
				if ((reqs != null) && (reqs.length() > 0)) {
					reqs = reqs.substring(0, reqs.length() - 2);
				}
				Utils.log("Optimization, For " + myFileName + " sending Requests only to Process(es): " + reqs);
				Utils.log("Remaining Replies: " + count);
				for (int i = 1; i <= 5; i++) {
					if (i != myProcessNum && requestagainF2[i]) {
						MutualExclusionHelper.sendRequestToProcess(myRequestTimestamp, myProcessNum, i, myFileName,
								writerForChannel);
					}
				}
			}

		} else if ((Constants.FILE3_NAME).equalsIgnoreCase(fileName)) {
			if (!myFirstCSBeginCompletedF3) {

				Utils.log("First CS for: " + fileName + ", Sending request to all processes");
				myPendingReplyCount = Constants.PROCESS_CHANNELS;

				int total = Constants.PROCESS_CHANNELS + 1;
				for (int i = 1; i <= total; i++) {
					if (i != myProcessNum) {
						MutualExclusionHelper.sendRequestToProcess(myRequestTimestamp, myProcessNum, i, myFileName,
								writerForChannel);
					}
				}
				myFirstCSBeginCompletedF3 = true;
			} else {
				Utils.log("CS for: " + fileName + " atleast completed once");
				String reqs = "";
				int count = 0;
				for (int i = 1; i <= 5; i++) {
					if (i != myProcessNum && requestagainF3[i]) {
						reqs = reqs + String.valueOf(i) + ", ";
						count++;
					}
				}
				myPendingReplyCount = count;
				if ((reqs != null) && (reqs.length() > 0)) {
					reqs = reqs.substring(0, reqs.length() - 2);
				}
				Utils.log("Optimization, For " + myFileName + " sending Requests only to Process(es): " + reqs);
				Utils.log("Remaining Replies: " + count);
				for (int i = 1; i <= 5; i++) {
					if (i != myProcessNum && requestagainF3[i]) {
						MutualExclusionHelper.sendRequestToProcess(myRequestTimestamp, myProcessNum, i, myFileName,
								writerForChannel);
					}
				}
			}

		}
		while (myPendingReplyCount > 0) {
			try {
				Thread.sleep(Constants.CHECK_PENDING_COUNT);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		updatingrequestagain = true;
		if ((Constants.FILE1_NAME).equalsIgnoreCase(fileName)) {
			for (int i = 1; i <= 5; i++)
				requestagainF1[i] = false;
		} else if ((Constants.FILE2_NAME).equalsIgnoreCase(fileName)) {
			for (int i = 1; i <= 5; i++)
				requestagainF2[i] = false;
		} else if ((Constants.FILE3_NAME).equalsIgnoreCase(fileName)) {
			for (int i = 1; i <= 5; i++)
				requestagainF3[i] = false;
		}
		updatingrequestagain = false;

		Utils.log("Got all replies for " + myFileName + ", setting 'requestAgainQueue' to false for all processes");
		return true;

	}

	/**
	 * Method that's called when the client critical section ends It sends out all
	 * the deferred replies and clears its queue
	 */
	public void myCSRequestEnd() {
		String fileName = myFileName;
		myRequestCSFlag = false;
		myFileName = "";

		Collections.sort(myDeferredReplies, DeferredReply.DREP_COMP);
		Utils.log("Total Deferred Replies:" + myDeferredReplies.size());
		for (DeferredReply dr : myDeferredReplies) {
			MutualExclusionHelper.sendReplyToProcess(dr.getProcessNum(), writerForChannel, myProcessNum);// replyTo(dr.getProcessNum());
			if ((Constants.FILE1_NAME).equalsIgnoreCase(fileName)) {
				requestagainF1[dr.getProcessNum()] = true;
			} else if ((Constants.FILE2_NAME).equalsIgnoreCase(fileName)) {
				requestagainF2[dr.getProcessNum()] = true;
			} else if ((Constants.FILE3_NAME).equalsIgnoreCase(fileName)) {
				requestagainF3[dr.getProcessNum()] = true;
			}
		}
		myDeferredReplies.clear();
	}

	/**
	 * Method that's called when the owning client receives a request
	 * 
	 * @param senderTimestamp
	 * @param senderProcessNum
	 * @param senderFileName
	 */
	public void myReceivedRequest(Timestamp senderTimestamp, int senderProcessNum, String senderFileName) {
		Utils.log("-->Received REQUEST from Process:" + senderProcessNum + " ,SenderTimestamp:" + senderTimestamp
				+ " ,File:" + senderFileName);
		while (updatingrequestagain) {
			try {
				Utils.log("Holding on..");
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		boolean deferOutcome = MutualExclusionHelper.evaluateDeferCondition(myRequestCSFlag, senderTimestamp,
				myRequestTimestamp, senderProcessNum, myProcessNum, senderFileName, myFileName);
		if (deferOutcome) {
			Utils.log("-->DEFERRED sending message to Process:" + senderProcessNum);
			myDeferredReplies.add(new DeferredReply(true, senderProcessNum, senderTimestamp));

		} else {
			MutualExclusionHelper.sendReplyToProcess(senderProcessNum, writerForChannel, myProcessNum);
			if (executingCSFlag || finishedCSFlag) {
				if ((Constants.FILE1_NAME).equalsIgnoreCase(senderFileName)) {
					requestagainF1[senderProcessNum] = true;
				} else if ((Constants.FILE2_NAME).equalsIgnoreCase(senderFileName)) {
					requestagainF2[senderProcessNum] = true;
				} else if ((Constants.FILE3_NAME).equalsIgnoreCase(senderFileName)) {
					requestagainF3[senderProcessNum] = true;
				}
			}
		}
	}

	/**
	 * Method that's called when the owning client receives a reply for its request
	 */
	public void myReceivedReply() {
		int curr = myPendingReplyCount - 1;
		if (curr > 0)
			myPendingReplyCount = curr;
		else
			myPendingReplyCount = 0;
		Utils.log("---->Remaining Replies: " + myPendingReplyCount);
	}

	public String getFileCSAccess() {
		return myFileName;
	}

	public void setFileCSAccess(String fileCSAccess) {
		this.myFileName = fileCSAccess;
	}

	public String getMyRequestTimestamp() {
		return "[" + myRequestTimestamp + "]";
	}

	public PrintWriter[] getWriterForChannel() {
		return writerForChannel;
	}

	public void setWriterForChannel(PrintWriter[] writerForChannel) {
		this.writerForChannel = writerForChannel;
	}

}
