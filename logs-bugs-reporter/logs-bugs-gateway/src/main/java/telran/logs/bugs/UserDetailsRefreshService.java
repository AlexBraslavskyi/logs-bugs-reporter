package telran.logs.bugs;

import static telran.logs.bugs.accounting.api.AccountingApiConstants.GET_ACTIVATED_ACCOUNTS;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import telran.logs.bugs.accounting.dto.AccountResponse;
import telran.logs.bugs.client.LoadBalancer;


@Service
public class UserDetailsRefreshService extends Thread {
	static Logger LOG = LoggerFactory.getLogger(UserDetailsRefreshService.class);
	RestTemplate restTemplate = new RestTemplate();
	@Value("${app-accounting-username:user}")
	String username;
	@Value("${app-accounting-password:****}")
	String password;
	@Value("${app-refresh-timeout:30000}")
	long timeout;
	@Value("${app-accounting-provider:accounting-provider}")
	String accountingProvier;

	public UserDetailsRefreshService() {
		setDaemon(true);
	}

	@Autowired
	ConcurrentHashMap<String, UserDetails> users;

	@Autowired
	LoadBalancer loadBalancer;

	@Bean
	ConcurrentHashMap<String, UserDetails> getUsersMap() {
		return new ConcurrentHashMap<>();
	}

	@Override
	@SneakyThrows
	public void run() {
		while (true) {
			fillUserDetails();
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void fillUserDetails() {
		AccountResponse[] accounts = getAccounts();
		fillUsers(accounts);
	}

	private AccountResponse[] getAccounts() {

		String baseUrl = loadBalancer.getBaseUrl(accountingProvier);

		LOG.debug("accountingProvier: {}{}", accountingProvier, GET_ACTIVATED_ACCOUNTS);
		ResponseEntity<AccountResponse[]> response = restTemplate.exchange(baseUrl + GET_ACTIVATED_ACCOUNTS,
				HttpMethod.GET, null, AccountResponse[].class);
		AccountResponse[] accounts = response.getBody();
		LOG.debug("accounts: {}", Arrays.deepToString(accounts));
		return accounts;
	}

	private void fillUsers(AccountResponse[] accounts) {

		Arrays.stream(accounts).map(a -> new User(a.username, a.password, getAuthorities(a)))
				.forEach(ud -> users.put(ud.getUsername(), ud));

	}

	private Collection<? extends GrantedAuthority> getAuthorities(AccountResponse account) {
		String[] roles = Arrays.stream(account.roles).map(r -> "ROLE_" + r).toArray(String[]::new);
		return AuthorityUtils.createAuthorityList(roles);
	}

	private String getAuthToken() {
		String tokenText = String.format("%s:%s", username, password);
		return "Basic " + Base64.getEncoder().encodeToString(tokenText.getBytes());
	}
}
