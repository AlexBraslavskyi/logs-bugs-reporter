package telran.security.accounting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfiguration {
	@Value("${user-password}")
	String passwordUser;
	@Value("${admin-password}")
	String passwordAdmin;
	@Bean
	MapReactiveUserDetailsService getMapDetails() {
		
		UserDetails user1 = new User("Moshe", "{noop}"+passwordUser, AuthorityUtils.createAuthorityList("ROLE_USER"));
		UserDetails user2 = new User("David", "{noop}"+passwordAdmin, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
	
		UserDetails users[] = {user1,user2};
		return new MapReactiveUserDetailsService(users);
	}

	@Bean
	SecurityWebFilterChain securityFiltersChain(ServerHttpSecurity httpSecurity) {
		
		SecurityWebFilterChain securityFiltersChain = httpSecurity.csrf()
				.disable().httpBasic().and().authorizeExchange()
				.pathMatchers(HttpMethod.GET).hasRole("USER")
				.pathMatchers(HttpMethod.POST).hasRole("ADMIN")
				.pathMatchers(HttpMethod.PUT).hasRole("ADMIN")
				.pathMatchers(HttpMethod.DELETE).hasRole("ADMIN")
				.and().build();
		
		return securityFiltersChain;
	}

}
