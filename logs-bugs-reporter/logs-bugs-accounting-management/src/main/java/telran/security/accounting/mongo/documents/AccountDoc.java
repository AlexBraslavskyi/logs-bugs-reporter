package telran.security.accounting.mongo.documents;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")

public class AccountDoc {

	@Id
	public String userName;
	public String password;
	public long activationTimestemp;
	public String[] roles;
	public long expirationTimestemp;
	public AccountDoc(String userName, String password, long activationTimestemp, String[] roles, long expirationTimestemp) {
		super();
		this.userName = userName;
		this.password=password;
		this.activationTimestemp = activationTimestemp;
		this.roles = roles;
		this.expirationTimestemp = expirationTimestemp;
	}
	public AccountDoc() {
	
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public long getActivationTimestemp() {
		return activationTimestemp;
	}
	public String[] getRoles() {
		return roles;
	}
	public long getExpirationTimestemp() {
		return expirationTimestemp;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (activationTimestemp ^ (activationTimestemp >>> 32));
		result = prime * result + (int) (expirationTimestemp ^ (expirationTimestemp >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + Arrays.hashCode(roles);
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountDoc other = (AccountDoc) obj;
		if (activationTimestemp != other.activationTimestemp)
			return false;
		if (expirationTimestemp != other.expirationTimestemp)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (!Arrays.equals(roles, other.roles))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AccountDoc [userName=" + userName + ", password=" + password + ", activationTimestemp="
				+ activationTimestemp + ", roles=" + Arrays.toString(roles) + ", expirationTimestemp="
				+ expirationTimestemp + "]";
	}
	

}
