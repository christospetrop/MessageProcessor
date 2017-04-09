/**
 * Simple Message Process
 * @author Christos Petropoulos <christospetrop@gmail.com>
 * @version 0.1.0
 * 
 * This class simulates a message notification receiver
 * Compliance execution J2SE-1.6 and above. 
 */
package com.message.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageListener implements Runnable  {
	private final LinkedBlockingQueue<String> messageQueue;
	private final SalesReportObj  report;
	private final List<String> messageRecords;
	private final List<String> messageBucket = new ArrayList<String>(); //Any aborted or non accepted message goes in this list
	private final int MAX_CAPACITY;
	
	private String MSG_TYPE_1_PATTERN = "^\\w+\\sat\\s\\d+\\D$";
	private String MSG_TYPE_2_PATTERN = "^\\d+\\ssales\\sof\\s\\w+\\sat\\s\\d+\\D$";
	private String MSG_TYPE_3_PATTERN = "^(add|subtract|multiply)\\s\\d+\\D\\s\\w+$";

	public MessageListener(LinkedBlockingQueue<String> sharedQueue,List<String> sharedMessageRecords, SalesReportObj sharedReport, int size) {
		this.messageQueue = sharedQueue;
		this.messageRecords = sharedMessageRecords;
		this.MAX_CAPACITY = size;
		this.report = sharedReport;
	}

	@Override
	public void run() {
		
		LinkedBlockingQueue<String> testData = IncommingMsgInit();		
		System.out.println("Dummy data  size: " + testData.size());
		while (testData.size() !=0) {
			try {
				messageRecorder(testData.poll());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Records incomming messages and feeds a message queue for Message Processor
	 * @param msg
	 * @throws InterruptedException
	 */
	private void messageRecorder(String msg) throws InterruptedException {
		synchronized (messageRecords) {			
			
			if  (messageRecords.size() == MAX_CAPACITY) {
				
				System.out.println("Message Queue is full " + Thread.currentThread().getName() + " is not accepting any new message, size: "
						+ messageRecords.size());
				
				String leftAlignFormat = "| %-15s | %-5d | %-5d | %-5d |%n";
				System.out.format("%n============ Final Sales Report ===========%n");
				System.out.format("+-----------------+-------+-------+-------+%n");
				System.out.format("| Product         | Sales | Price | Total |%n");
				System.out.format("+-----------------+-------+-------+-------+%n");				
				for(Entry<String, ProductObject> e : report.getSalesReport().entrySet()) {
					System.out.format(leftAlignFormat, e.getKey(), e.getValue().getSales(), e.getValue().getPrice(),e.getValue().getTotal());
			    }
				System.out.format("+-----------------+-------+-------+-------+%n");
				System.out.println("Messages processed: " + messageRecords.size());
				System.out.println("...Application Paused...");
				messageRecords.wait();
				Thread.currentThread().interrupt();
			} else {
				
				if (messageRecords.size() > 0 && messageRecords.size() < 50 && messageRecords.size() % 10 == 0) {
					System.out.println("============== Sales Report After " + messageRecords.size() + "messages  ==============");
					String leftAlignFormat = "| %-15s | %-5d | %-5d | %-5d |%n";
					System.out.format(" Sales Report After " + messageRecords.size() + "messages %n");
					System.out.format("+-----------------+-------+-------+-------+%n");
					System.out.format("| Product         | Sales | Price | Total |%n");
					System.out.format("+-----------------+-------+-------+-------+%n");				
					for(Entry<String, ProductObject> e : report.getSalesReport().entrySet()) {
						System.out.format(leftAlignFormat, e.getKey(), e.getValue().getSales(), e.getValue().getPrice(),e.getValue().getTotal());
				    }
					System.out.format("+-----------------+-------+-------+-------+%n");

				}
				
				
				// Collects only the messages with the correct message type
				// Messages stored to bucket for archive
				if (msg.toLowerCase().matches(MSG_TYPE_1_PATTERN) || msg.toLowerCase().matches(MSG_TYPE_2_PATTERN) || msg.toLowerCase().matches(MSG_TYPE_3_PATTERN)) {
					messageRecords.add(msg);
					System.out.println("Messages stored: " + msg);
					messageForProcess(msg);
				} else {
					System.out.println("Message Type Unknown, dropped to bucket "+ messageRecords.size());
					messageBucket.add(msg);
					
				}
				messageRecords.notifyAll();
				Thread.sleep(1000);//delay for testing purpose
			}
			
		}
	}
	
	private void messageForProcess(String msg) throws InterruptedException {
		synchronized (messageQueue) {
			messageQueue.add(msg);
			System.out.println("Message in queue for process: " + msg);
			messageQueue.notifyAll();

		}
	}
	
	/**
	 * Feeds Message Listener with dummy messages (for testing only)
	 * @return Messages list
	 */
	private LinkedBlockingQueue<String> IncommingMsgInit(){
		LinkedBlockingQueue<String> msgList = new LinkedBlockingQueue<String>();
		
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("10 sales of apples at 10p");
		msgList.add("10 sales of oranges at 20p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("10 sales of apples at 10p");
		msgList.add("10 sales of oranges at 20p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("10 sales of apples at 10p");
		msgList.add("10 sales of oranges at 20p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("20 sales of apples at 10p");
		msgList.add("20 sales of oranges at 20p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("Add 5p oranges");
		msgList.add("Multiply 2p apples");
		msgList.add("Add 5p oranges");
		msgList.add("Multiply 2p oranges");
		msgList.add("subtract 5p oranges");
		msgList.add("subtract 5p apples");
		msgList.add("Multiply 2p apples");
		msgList.add("Add 5p oranges");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("apples at 10p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		msgList.add("oranges at 20p");
		
		return msgList;
	}
}
