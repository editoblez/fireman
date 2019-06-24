package com.ec.fireman.util;

import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.UserAccount;

public class UserUtil {

  public static UserAccount createDefaultAdminUtil(Role rolByName) {
    return new UserAccount("Admin", "Admin", "Admin", "Admin", "123", "ICy5YqxZB1uWSwcVLSNLcA==", "", rolByName);
  }
}
