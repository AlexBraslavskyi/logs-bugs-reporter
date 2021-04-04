package telran.logs.bugs;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class SecurityConfig {
	public static final String LOGS = "/logs";
	public static final String BUGS = "/bugs";
	public static final String OPEN = "/open";
	public static final String CLOSE = "/close";
	public static final String ASSIGN = "/assign";
	public static final String ARTIFACTS = "/artifacts";
	public static final String PROGRAMMERS = "/programmers";
	public static final String ANY = "/**";


	@Autowired
	UserDetailsRefreshService refreshService;

	@Autowired
	ConcurrentHashMap<String, UserDetails> users;

	@Bean
	MapReactiveUserDetailsService getMapDetails() {
		return new MapReactiveUserDetailsService(users);
	}

	@Bean
	SecurityWebFilterChain securityFiltersChain(ServerHttpSecurity httpSecurity) {
		SecurityWebFilterChain securityFiltersChain = httpSecurity.csrf().disable().httpBasic().and()
				.authorizeExchange().pathMatchers(HttpMethod.GET, LOGS + ANY).hasRole("DEVELOPER")
				.pathMatchers(HttpMethod.POST, BUGS + OPEN).hasAnyRole("TESTER", "ASSIGNER", "DEVELOPER")
				.pathMatchers(HttpMethod.POST, BUGS + OPEN + ASSIGN).hasAnyRole("TESTER", "ASSIGNER", "DEVELOPER")
				.pathMatchers(HttpMethod.PUT, BUGS + ASSIGN).hasRole("ASSIGNER")
				.pathMatchers(HttpMethod.PUT, BUGS + CLOSE).hasRole("TESTER")
				.pathMatchers(HttpMethod.POST, BUGS + PROGRAMMERS).hasRole("PROJECT_OWNER")
				.pathMatchers(HttpMethod.POST, BUGS + ARTIFACTS).hasAnyRole("TEAM_LEAD", "ASSIGNER")
				.pathMatchers(HttpMethod.GET, BUGS + ANY).authenticated().and()
				.build();
		return securityFiltersChain;
	}

	@PostConstruct
	void updateMapUserDetails() throws InterruptedException {
		refreshService.start();

	}
}
