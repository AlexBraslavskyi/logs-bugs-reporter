package telran.security.accounting.controllers;

import static telran.security.accounting.api.ApiConstants.ADD;
import static telran.security.accounting.api.ApiConstants.ADD_ROLE;
import static telran.security.accounting.api.ApiConstants.DEL;
import static telran.security.accounting.api.ApiConstants.GET;
import static telran.security.accounting.api.ApiConstants.ID;
import static telran.security.accounting.api.ApiConstants.PASS;
import static telran.security.accounting.api.ApiConstants.REMOVE_ROLE;
import static telran.security.accounting.api.ApiConstants.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.security.accounting.dto.AccountRequest;
import telran.security.accounting.dto.AccountResponse;
import telran.security.accounting.service.AccountingManagement;


@RestController
@Validated
public class AccountingManagementController {
	static Logger LOG = LoggerFactory.getLogger(AccountingManagementController.class);
	@Autowired(required = true)
	AccountingManagement accountManagment;
	
	@GetMapping(value = URL+GET)
	AccountResponse getAccount(@PathVariable(ID) @NotEmpty String userName) {
		AccountResponse response =accountManagment.getAccount(userName);
		LOG.debug("recieved account of user {}", userName);
		return response;
	}
	
	@PostMapping(URL+ADD)
	AccountResponse addAccount(@RequestBody(required = true) @Valid AccountRequest accountDto) {
		AccountResponse response = accountManagment.addAccount(accountDto);
		LOG.debug("account  {} was added", accountDto);
		return response;
	}
	
	@PutMapping(URL+PASS)
	AccountResponse updatePassword(@PathVariable(ID) @NotEmpty String userName, @RequestBody(required = true) @NotEmpty String password) {
		AccountResponse response = accountManagment.updatePassword(userName, password);
		LOG.debug("was updated password for user {}", userName);
		return response;
	}
	
	@PutMapping(URL+ADD_ROLE)
	AccountResponse addRole(@PathVariable(ID)@NotEmpty String userName, @RequestBody(required = true) @NotEmpty String role) {
		AccountResponse response = accountManagment.addRole(userName, role);
		LOG.debug("was added role for user {}", userName);
		return response;
	}
	
	@PutMapping(URL+REMOVE_ROLE)
	AccountResponse removeRole(@PathVariable(ID) @NotEmpty String userName, @RequestBody(required = true) @NotEmpty String role) {
		AccountResponse response = accountManagment.removeRole(userName, role);
		LOG.debug("was removed role for user {}", userName);
		return response;
	}
	@DeleteMapping(URL+DEL)
	void deleteAccount(@PathVariable(ID) @NotEmpty String userName) {
		accountManagment.deleteAccount(userName);
		LOG.debug("account of user {} was deleted", userName);
	}

}
