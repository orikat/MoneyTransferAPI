/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.revolut.models.UserAccount;

/**
 * Manage user CRUD operations
 * It will call data access layer if exists**/

public class UserManager {
    private static ConcurrentHashMap<Long, UserAccount> users = new ConcurrentHashMap<Long, UserAccount>();

    public static UserAccount getUserAccount(Long id) {
        return users.get(id);
    }
    
    public static void addUserAccount(UserAccount userAccount) {
        users.put(userAccount.getId(), userAccount);
    }
    
    public static ArrayList<UserAccount> getAllUsers() {
        ArrayList<UserAccount> localUsers = new ArrayList<>();
        for (Entry<Long, UserAccount> userAccount : users.entrySet()) {
            localUsers.add(userAccount.getValue());
        }
        return localUsers;
    }
    
    public static void removeUser(Long id) {
        users.remove(id);
    }
}
