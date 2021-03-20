package telran.security.accounting.mongo.repo;

import telran.security.accounting.dto.AccountRequest;
import telran.security.accounting.dto.AccountResponse;

public interface UpdateMongoOperations {
	AccountResponse addAccount(AccountRequest accountDto);
	AccountResponse addRole(String username, String role);
	AccountResponse updatePassword(String username, String password);
	AccountResponse removeRole(String username, String role);
	AccountResponse getAccount(String username);
	
}
