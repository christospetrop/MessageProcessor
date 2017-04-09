/**
 * Simple message process
 * @author Christos Petropoulos <christospetrop@gmail.com>
 * @version 0.1.0
 * 
 * Compliance execution J2SE-1.6 and above. 
 */
package com.message.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class main {
	
	public static void main(String[] args) {
		
		LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
		List<String> messageRecords = new ArrayList<String>();
		final SalesReportObj report = new SalesReportObj();
		int MAX_CAPACITY = 50;
	
		Thread tMessageListener = new Thread(new MessageListener(messageQueue, messageRecords, report,MAX_CAPACITY), "Message Listener");
		Thread tMessageProcessor = new Thread(new MessageProcessor(messageQueue,report, MAX_CAPACITY), "Message Processor");
		tMessageListener.start();
		tMessageProcessor.start();
		
	}

}
