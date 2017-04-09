package com.message.process;

import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageProcessor implements Runnable {
	private final LinkedBlockingQueue<String> messageQueue;
	private ProductObject product;
	private final SalesReportObj  report;
	private String message;
	private Integer maxCapacity;
	//private Boolean pauseExecution;
	
	private String MSG_TYPE_1_PATTERN = "^\\w+\\sat\\s\\d+\\D$";
	private String MSG_TYPE_2_PATTERN = "^\\d+\\ssales\\sof\\s\\w+\\sat\\s\\d+\\D$";
	//private String MSG_TYPE_3_PATTERN = "^(add|subtract|multiply)\\s\\d+\\D\\s\\w+$";

	//public MessageProcessor(LinkedBlockingQueue<String> sharedQueue, SalesReportObj sharedReport, Integer maxCapacity, Boolean pauseExecution) {
	public MessageProcessor(LinkedBlockingQueue<String> sharedQueue, SalesReportObj sharedReport, Integer maxCapacity) {
		this.messageQueue = sharedQueue;
		this.report = sharedReport;
		this.maxCapacity = maxCapacity;
		//this.pauseExecution = pauseExecution;
	}

	@Override
	public void run() {
		while (true) {
			try {
				processor();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void processor() throws InterruptedException {
		synchronized (messageQueue) {
			while (messageQueue.isEmpty()) {
				//System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: "b+ messageQueue.size());
				messageQueue.wait();
			}
			
			message = messageQueue.poll();
			String[] tmpList =  message.split("\\s");
			if(message.toLowerCase().matches(MSG_TYPE_1_PATTERN)) {
				System.out.println("Msg Type 1: " + message);
				addToReportT1(tmpList);
			} else if(message.toLowerCase().matches(MSG_TYPE_2_PATTERN)) {
				System.out.println("Msg Type 2: " + message);
				addToReportT2(tmpList);
			} else {
				System.out.println("Msg Type 3: " + message);
				addToReportT3(tmpList);
			}
			
			messageQueue.notifyAll();
			
//			if (report.getSalesReportSize() == maxCapacity) {
//				MessageListenerInterrupt();
//			}
			
		}
	}
	
//	private void MessageListenerInterrupt() throws InterruptedException {
//		synchronized (pauseExecution) {
//			pauseExecution = true;
//			pauseExecution.notifyAll();
//		}
//	}
	
	private void addToReportT1(String[] msg) throws InterruptedException {
		synchronized (report) {
			Integer sales;
			Integer price;
			Integer total;
			
			if(msg.length > 0) {
				if(report.getProductSales(msg[0]) == null){
					sales = 1;
					price = Integer.parseInt(msg[2].replaceAll("[\\D]", ""));
					total = sales * price;
					product = new ProductObject(sales, price, total);
					report.addSales(msg[0], product);
				} else {
					sales = report.getProductSales(msg[0]).getSales() + 1;
					price = Integer.parseInt(msg[2].replaceAll("[\\D]", ""));
					total = sales * price;
					report.getProductSales(msg[0]).setPrice(price);
					report.getProductSales(msg[0]).setSales(sales);
					report.getProductSales(msg[0]).setTotal(total);
				}	
			} else {
				System.out.println("Message list is empty!");
				report.wait();
			}
			 
			report.notifyAll();
		}
	}
	
	private void addToReportT2(String[] msg) throws InterruptedException {
		synchronized (report) {
			Integer sales;
			Integer price;
			Integer total;
			
			if(msg.length > 0) {
				if(report.getProductSales(msg[3]) == null){
					sales = Integer.parseInt(msg[0]);
					price = Integer.parseInt(msg[5].replaceAll("[\\D]", ""));
					total = sales * price;
					product = new ProductObject(sales, price, total);
					report.addSales(msg[3], product);
				} else {
					sales = report.getProductSales(msg[3]).getSales() + Integer.parseInt(msg[0]);
					price = Integer.parseInt(msg[5].replaceAll("[\\D]", ""));
					total = sales * price;
					report.getProductSales(msg[3]).setPrice(price);
					report.getProductSales(msg[3]).setSales(sales);
					report.getProductSales(msg[3]).setTotal(total);
				}
			} else {
				System.out.println("Message list is empty!");
				report.wait();
			}
			 
			report.notifyAll();
		}
	}
	
	private void addToReportT3(String[] msg) throws InterruptedException {
		synchronized (report) {
			Integer sales;
			Integer price;
			Integer total;
			
			if(msg.length > 0) {
				if(report.getProductSales(msg[2]) == null){
					System.out.println("Product type does not exist");
					report.wait();
				} else {
					sales = report.getProductSales(msg[2]).getSales();
					if(msg[0].toLowerCase().equals("add"))
						price = report.getProductSales(msg[2]).getPrice() + Integer.parseInt(msg[1].replaceAll("[\\D]", ""));
					else if(msg[0].toLowerCase().equals("subtract"))
						price = report.getProductSales(msg[2]).getPrice() - Integer.parseInt(msg[1].replaceAll("[\\D]", ""));
					else 
						price = report.getProductSales(msg[2]).getPrice() * Integer.parseInt(msg[1].replaceAll("[\\D]", ""));
					
					total = sales * price;
					report.getProductSales(msg[2]).setPrice(price);
					report.getProductSales(msg[2]).setSales(sales);
					report.getProductSales(msg[2]).setTotal(total);
				}	
			} else {
				System.out.println("Message list is empty!");
				report.wait();
			}
			 
			report.notifyAll();
		}
	}
	
}
