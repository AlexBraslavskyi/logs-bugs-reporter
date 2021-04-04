package telran.logs.bugs.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import telran.logs.bugs.accounting.mongo.documents.AccountDoc;
import telran.logs.bugs.accounting.mongo.repo.AccountRepository;

@RestController
public class AccountsProviderController {
	static Logger LOG = LoggerFactory.getLogger(AccountsProviderController.class);
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping(value="/active_accounts", produces="application/stream+json")
	Flux<AccountDoc> getAllLogs() {
		Flux<AccountDoc> result = accountRepository.findByExpirationTimestampGreaterThan(System.currentTimeMillis() / 1000);
		LOG.debug("Logs sent");
		return result;
	}

}
