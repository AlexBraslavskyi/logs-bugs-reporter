package telran.logs.bugs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import telran.logs.bugs.accounting.mongo.documents.AccountDoc;
import telran.logs.bugs.accounting.mongo.repo.AccountRepository;

@SpringBootTest
@AutoConfigureWebTestClient
public class AccountsProviderTest {
	static Logger LOG = LoggerFactory.getLogger(AccountsProviderTest.class);
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	AccountRepository accountRepository;
	
	List<AccountDoc> listAccountDocs = Arrays.asList(
			
			new AccountDoc("Moshe", System.currentTimeMillis()/1000, "123456", new String[] {"user"}, System.currentTimeMillis()/1000 + 10000),
			new AccountDoc("Sara", System.currentTimeMillis()/1000, "123456", new String[] {"user"}, System.currentTimeMillis()/1000 + 10000));
	
	@Test
	void emailExistTest() {
		accountRepository.saveAll(listAccountDocs).blockLast();
		LOG.debug("Seved: {} accounts", accountRepository.count());
		
		List<AccountDoc> listAccounts = webTestClient.get()
		.uri("/active_accounts").exchange()
		.expectStatus().isOk()
		.expectBodyList(AccountDoc.class)
		.returnResult()
		.getResponseBody();
		
		assertTrue(listAccountDocs.containsAll(listAccounts));
		assertEquals(listAccounts.size(),listAccountDocs.size());
	}

}
