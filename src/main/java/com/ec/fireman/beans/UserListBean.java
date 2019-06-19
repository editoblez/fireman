package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.UserAccount;

@Named
@SessionScoped
public class UserListBean implements Serializable {

	private static final long serialVersionUID = -5468228478359216158L;

	private static final Logger LOG = LogManager.getLogger(UserListBean.class);

	@Inject
	private UserAccountDao userAccountDao;

	private List<UserAccount> users;
	private UserAccount selectedUser;
	private String role;
	private String passReset1;
	private String passReset2;

	@PostConstruct
	public void init() {
		this.refreshUsers();
		selectedUser = new UserAccount();
	}

	public void refreshUsers() {
		users = userAccountDao.findAll();
		LOG.info("Users length: " + (users != null ? users.size() : 0));
	}

	public void createUser() {
		// TODO: SAVE USER
		this.refreshUsers();
		selectedUser = new UserAccount();
	}
	
	public void editUser() {
		// TODO: SAVE USER
		this.refreshUsers();
		selectedUser = new UserAccount();
	}

	public List<String> listRoles() {
		// TODO: SEARCH THE ROLES IN THE DATABASE 
		List<String> roles = new ArrayList<String>();
		roles.add("Administrador");
		roles.add("Inspector");
		roles.add("Contador");
		roles.add("Bombero");
		return roles;
	}
	
	public void resetPassword() {
		// TODO: SAVE NEW PASSWORD
		LOG.info("Password: " + passReset1 + " - " + passReset2);
		selectedUser = new UserAccount();
	}

	public List<UserAccount> getUsers() {
		return users;
	}

	public void setUsers(List<UserAccount> users) {
		this.users = users;
	}

	public UserAccount getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserAccount selectedUser) {
		this.selectedUser = selectedUser;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassReset1() {
		return passReset1;
	}

	public void setPassReset1(String passReset1) {
		this.passReset1 = passReset1;
	}

	public String getPassReset2() {
		return passReset2;
	}

	public void setPassReset2(String passReset2) {
		this.passReset2 = passReset2;
	}

}
