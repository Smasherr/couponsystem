package il.moran.couponsystem.client.dal;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 *class Company Define company details on system
 */
@XmlRootElement
public class Company {
	
	private long id;
	private String email;
	private String companyName;
	private String password;
	private Collection<Coupon> coupons;
	
	public Company(){
		coupons = new ArrayList<>();
	}

	public Company(long id) {
		this.id = id;
		coupons = new ArrayList<>();
	}
	

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons =coupons;
	}
	
	public void addCoupon(Coupon coupon){
		this.coupons.add(coupon);
	}
	
	public void removeCoupon(Coupon coupon){
		this.coupons.remove(coupon);
	}
	
	@Override
	public String toString() {
		String str = "ID: " + getId() + "\t|Company name: " + getCompanyName() + "\t|Password: " + getPassword() + "\t|Email: " + getEmail();
		for(Coupon coupon:getCoupons()){
			str+= "\n*" + coupon.toString();
		}
		return str;
	}
	
	
}
