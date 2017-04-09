/**
 * Sales Report Object
 * @author Christos Petropoulos <christospetrop@gmail.com>
 * @version 0.1.0
 * 
 * Compliance execution J2SE-1.6 and above. 
 */
package com.message.process;

import java.util.HashMap;
import java.util.Map;

public class SalesReportObj {
	private static HashMap<String, ProductObject> salesReport = new HashMap<String, ProductObject>();

	public SalesReportObj() {

	}

	public static void addSales(String product, ProductObject productObj){
		salesReport.put(product, productObj);
		System.out.println("Sales report size: " + salesReport.size());
	}
	
	public static ProductObject getProductSales(String product){
		return salesReport.get(product);
	}
	
	public static Map<String, ProductObject> getSalesReport(){
		return salesReport;
	}
	
	public static ProductObject removeProductFromReport(String product){
		return salesReport.remove(product);
	}
	
	public static int getSalesReportSize(){
		return salesReport.size();
	}
}
