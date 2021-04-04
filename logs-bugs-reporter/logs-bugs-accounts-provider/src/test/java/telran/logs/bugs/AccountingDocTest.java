package telran.logs.bugs;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import telran.logs.bugs.accounting.dto.AccountRequest;
import telran.logs.bugs.accounting.mongo.documents.AccountDoc;
import telran.logs.bugs.accounting.mongo.repo.AccountRepository;

	@ContextConfiguration(classes = AccountRepository.class)
	@ExtendWith(SpringExtension.class)
	@EnableAutoConfiguration
	@AutoConfigureDataMongo
	class AccountingDocTest {

		@Autowired
		AccountRepository accounts;

		@Test
		void docTest() throws InterruptedException {

			AccountRequest account = new AccountRequest("Moshe", "{noop} 12345", new String[] { "USER", "ADMIN" }, System.currentTimeMillis()/1000+10000);

			accounts.save(new AccountDoc(account.username, System.currentTimeMillis()/1000, account.password, 
					account.roles, account.expirationPeriodMinutes)).block();
			
			AccountDoc expected = accounts.findAll().blockFirst();
			assertEquals(account.username,expected.getUsername());
			assertEquals(account.password, expected.getPassword());
			assertArrayEquals(account.roles, expected.getRoles());
		}
	}
