package il.moran.couponsystem.client.dal;

/*
 *class  Coupon  define coupon details/fields in the system
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Coupon {

	private long id;
	private String title;;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	public Coupon() {

	}

	public Coupon(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = "Coupon ID: " + getId() + "\t|" + "Title: " + getTitle() + "\t|" + "Start Date: "
				+ df.format(getStartDate()) + "\t|" + "End Date: " + df.format(getEndDate()) + "\t|" + "Amount: "
				+ getAmount() + "\t|" + "Coupon Type: " + getType() + "\t|" + "Message: " + getMessage() + "\t|"
				+ "Price: " + getPrice() + "\t|" + "Image: " + getImage();
		return str;
	}

}
