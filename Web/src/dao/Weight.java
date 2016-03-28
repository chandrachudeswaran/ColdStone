package dao;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Weight {
	private int id;
	private float price;
	private String weight;
	private String status;
	private String userid="";
	private String timeinserted;
	private String dateinserted;
	private String toppings;
	private float toppingsrate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTimeinserted() {
		return timeinserted;
	}
	public void setTimeinserted(String timeinserted) {
		this.timeinserted = timeinserted;
	}
	public String getDateinserted() {
		return dateinserted;
	}
	public void setDateinserted(String dateinserted) {
		this.dateinserted = dateinserted;
	}
	
	public String getToppings() {
		return toppings;
	}
	public void setToppings(String toppings) {
		this.toppings = toppings;
	}
	public float getToppingsrate() {
		return toppingsrate;
	}
	public void setToppingsrate(float toppingsrate) {
		this.toppingsrate = toppingsrate;
	}
	@Override
	public String toString() {
		return "Weight [id=" + id + ", price=" + price + ", weight=" + weight + ", status=" + status + ", userid="
				+ userid + ", timeinserted=" + timeinserted + ", dateinserted=" + dateinserted + ", toppings="
				+ toppings + ", toppingsrate=" + toppingsrate + "]";
	}
	
	
	
	
	

}
