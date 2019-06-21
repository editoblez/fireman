package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.UserAccount;

@Named
@SessionScoped
public class UserListBean implements Serializable {

	private static final long serialVersionUID = -5468228478359216158L;

	private static final Logger LOG = LogManager.getLogger(UserListBean.class);
	private static final String MSG_ERROR_PASSWORD = "Las contraseñas no coinciden";

	@Inject
	private UserAccountDao userAccountDao;
	@Inject
	private RoleDao roleDao;

	private List<UserAccount> users;
	private UserAccount selectedUser;
	private Role role;
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

	@Transactional
	public void createUser() {
		selectedUser.setRole(role);
		userAccountDao.save(selectedUser);
		this.refreshUsers();
		selectedUser = new UserAccount();
	}

	@Transactional
	public void editUser() {
		selectedUser.setRole(role);
		userAccountDao.update(selectedUser);
		this.refreshUsers();
		selectedUser = new UserAccount();
	}
	
	@Transactional
	public String resetPassword() {
		if (passReset1 != passReset2) {
			return MSG_ERROR_PASSWORD;
		}
		selectedUser.setPassword(passReset1);
		userAccountDao.update(selectedUser);
		selectedUser = new UserAccount();
		return "OK";
	}

	public List<Role> listRoles() {
		return roleDao.findAll();
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
