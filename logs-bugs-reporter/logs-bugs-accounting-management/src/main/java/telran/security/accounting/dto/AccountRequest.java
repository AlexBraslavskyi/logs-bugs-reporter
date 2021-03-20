package telran.security.accounting.dto;

import java.util.Arrays;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AccountRequest {
	@NotEmpty
	public String userName;
	@Size(min = 8)
	public String password;
	public String[] roles;
	@Min(1)
	public long expiredPeriod;
	public AccountRequest(@NotEmpty String userName, @Size(min = 8) String password, String[] roles,
			@Min(1) long expiredPeriod) {
		super();
		this.userName = userName;
		this.password = password;
		this.roles = roles;
		this.expiredPeriod = expiredPeriod;
	}
	public AccountRequest() {
		
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
	public long getExpiredPeriod() {
		return expiredPeriod;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (expiredPeriod ^ (expiredPeriod >>> 32));
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
		AccountRequest other = (AccountRequest) obj;
		if (expiredPeriod != other.expiredPeriod)
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
		return "AccountRequest [userName=" + userName + ", password=" + password + ", roles=" + Arrays.toString(roles)
				+ ", expiredPeriod=" + expiredPeriod + "]";
	}
	
}
