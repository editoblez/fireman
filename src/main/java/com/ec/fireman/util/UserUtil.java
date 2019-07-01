package com.ec.fireman.util;

import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.UserAccount;

public class UserUtil {

  public static final String DEFAULT_ADMIN_CI = "123";

  public static UserAccount createDefaultAdminUtil(Role rolByName) {
    return new UserAccount("Admin", "Admin", "Admin", "Admin", DEFAULT_ADMIN_CI, "ICy5YqxZB1uWSwcVLSNLcA==", "", rolByName);
  }
}
