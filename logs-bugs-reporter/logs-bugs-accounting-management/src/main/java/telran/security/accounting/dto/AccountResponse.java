package telran.security.accounting.dto;

import java.util.Arrays;

public class AccountResponse {
	public String userName;
	public String password;
	public String[] roles;
	public long expirationTimestemp;
	public AccountResponse(String userName, String password, String[] roles,
			long expirationTimestemp) {
		super();
		this.userName = userName;
		this.password = password;
		this.roles = roles;
		this.expirationTimestemp = expirationTimestemp;
	}
	public AccountResponse() {

	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String[] getRoles() {
		return roles;
	}
	public long getExpirationTimestemp() {
		return expirationTimestemp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		AccountResponse other = (AccountResponse) obj;
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
		return "AccountResponse [userName=" + userName + ", password=" + password + ", roles=" + Arrays.toString(roles)
				+ ", expirationTimestemp=" + expirationTimestemp + "]";
	}
	

}
