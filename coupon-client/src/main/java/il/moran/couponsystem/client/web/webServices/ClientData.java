package il.moran.couponsystem.client.web.webServices;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientData {

	private String username;
	private String role;
	
	public ClientData() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	

}
