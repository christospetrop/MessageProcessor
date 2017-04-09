package com.message.process;

import java.util.List;

public class SalesReport implements Runnable {
	private final SalesReportObj report;

	public SalesReport(SalesReportObj sharedReport) {
		this.report = sharedReport;
	}

	@Override
	public void run() {
		while (true) {
			try {
				consume();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void consume() throws InterruptedException {
		synchronized (report) {
//			while (report.isEmpty()) {
//				System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: "
//						+ report.size());
//				report.wait();
//			}
//			Thread.sleep(5000);
//			int i = (Integer) report.remove(0);
//			System.out.println("Consumed: " + i);
//			report.notifyAll();
		}
	}
}
