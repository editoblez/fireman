package com.ec.fireman.beans;

import java.io.Serializable;
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

	private static final long serialVersionUID = 1386092170909655771L;
	private static final Logger LOG = LogManager.getLogger(UserListBean.class);

	@Inject
	private UserAccountDao userAccountDao;

	private List<UserAccount> users;
	private UserAccount selectedUser;

	@PostConstruct
	public void init() {
		this.refreshUsers();
		selectedUser = new UserAccount();
	}

	public void refreshUsers() {
		users = userAccountDao.findAll();
		LOG.info("Users length: " + (users != null ? users.size() : 0));
	}
	
	public void saveUser() {
		//TODO: SAVE USER
		this.refreshUsers();
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

}
