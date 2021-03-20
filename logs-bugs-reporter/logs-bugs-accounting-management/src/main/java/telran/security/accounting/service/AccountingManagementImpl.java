package telran.security.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.security.accounting.dto.AccountRequest;
import telran.security.accounting.dto.AccountResponse;
import telran.security.accounting.mongo.repo.AccountRepository;
@Service

public class AccountingManagementImpl implements AccountingManagement {
	@Autowired
	AccountRepository accountRepo;

	@Override
	public AccountResponse addAccount(AccountRequest accountDto) {

		return accountRepo.addAccount(accountDto);
	}

	@Override
	public void deleteAccount(String username) {
		accountRepo.deleteByUserName(username);

	}

	@Override
	public AccountResponse getAccount(String username) {

		return accountRepo.getAccount(username);
	}

	@Override
	public AccountResponse updatePassword(String username, String password) {

		return accountRepo.updatePassword(username, password);
	}

	@Override
	public AccountResponse addRole(String username, String role) {

		return accountRepo.addRole(username, role);
	}

	@Override
	public AccountResponse removeRole(String username, String role) {

		return accountRepo.removeRole(username, role);
	}

}
