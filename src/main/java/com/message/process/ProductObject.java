/**
 * Simple Message Process
 * @author Christos Petropoulos <christospetrop@gmail.com>
 * @version 0.1.0
 */
package com.message.process;

public class ProductObject {
	
	//Product number of sales
	private Integer sales;
	//Product price
	private Integer price;
	//Product Total
	private Integer total;
	
	/**
	 * @param sales
	 * @param price
	 * @param total
	 */
	public ProductObject(Integer sales, Integer price, Integer total) {
		this.sales = sales;
		this.price = price;
		this.total = total;
	}

	/**
	 * @return the sales
	 */
	public Integer getSales() {
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(Integer sales) {
		this.sales = sales;
	}

	/**
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductObject [sales=" + sales + ", price=" + price + ", total=" + total + "]";
	}
}
