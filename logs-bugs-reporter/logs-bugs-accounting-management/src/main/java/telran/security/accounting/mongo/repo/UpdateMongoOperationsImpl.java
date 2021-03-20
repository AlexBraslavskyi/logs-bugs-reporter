package telran.security.accounting.mongo.repo;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import telran.security.accounting.dto.AccountRequest;
import telran.security.accounting.dto.AccountResponse;
import telran.security.accounting.mongo.documents.AccountDoc;

public class UpdateMongoOperationsImpl implements UpdateMongoOperations {

	@Autowired
	MongoTemplate mongoTemplate;

	private AccountDoc getAccountDoc(String username) throws RuntimeException {
		AccountDoc accountDoc = mongoTemplate.findById(username, AccountDoc.class);
		if (accountDoc == null) {
			throw new RuntimeException(username + "dosn't exists");
		}
		return accountDoc;
	}

	@Override
	public AccountResponse addAccount(AccountRequest accountDto) {

		if (mongoTemplate.findById(accountDto.getUserName(), AccountDoc.class) != null) {
			AccountDoc accountDoc = new AccountDoc(accountDto.getUserName(), accountDto.getPassword(),
					System.currentTimeMillis() / 1000, accountDto.getRoles(), accountDto.getExpiredPeriod());
			mongoTemplate.save(accountDoc);
			return new AccountResponse(accountDto.getUserName(), accountDto.getPassword(), accountDto.getRoles(),
					accountDto.getExpiredPeriod());

		}
		throw new RuntimeException(accountDto.getUserName() + " already exists");
	}

	
	@Transactional
	@Override
	public AccountResponse addRole(String username, String role) {
		AccountDoc accountDoc = getAccountDoc(username);
		Set<String> roles = new HashSet<>(Arrays.asList(accountDoc.getRoles()));
		roles.add(role);
		accountDoc.setRoles(roles.toArray(new String[0]));
		return docToResponse(accountDoc);
	}

	@Transactional
	@Override
	public AccountResponse updatePassword(String username, String password) {
		AccountDoc accountDoc = getAccountDoc(username);
		accountDoc.setPassword(password);
		return docToResponse(accountDoc);
	}

	@Transactional
	@Override
	public AccountResponse removeRole(String username, String role) {
		AccountDoc accountDoc = getAccountDoc(username);
		Set<String> roles = new HashSet<>(Arrays.asList(accountDoc.getRoles()));
		roles.remove(role);
		accountDoc.setRoles(roles.toArray(new String[0]));
		return docToResponse(accountDoc);
	}

	private AccountResponse docToResponse(AccountDoc accountDoc) {
		return new AccountResponse(accountDoc.getUserName(), accountDoc.getPassword(), accountDoc.getRoles(),
				accountDoc.getExpirationTimestemp());
	}

	@Override
	public AccountResponse getAccount(String username) {
		
		return docToResponse(getAccountDoc(username));
	}

}
