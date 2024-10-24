package com.masai.utils;

import com.masai.entities.Admin;
import com.masai.exceptions.UserDoesNotExist;
import com.masai.repository.AdminRepository;

public class AdminValidator {
    public static void validateCredentials(Admin admin, AdminRepository adminDao) {
        Admin existingAdmin = adminDao.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        if (existingAdmin == null) {
            throw new UserDoesNotExist("Username or Password is wrong");
        }
    }
}
