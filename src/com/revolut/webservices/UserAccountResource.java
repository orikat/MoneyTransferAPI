/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.webservices;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.revolut.managers.UserManager;
import com.revolut.models.Response;
import com.revolut.models.UserAccount;

/**
 * web resource to add/get/getAll/delete users
 * **/
@Path("/users")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class UserAccountResource {
    /**
     * Add user account in local storage
     * @param user account object needs to be added
     * @return response holding result message and status code(200) if successefull
     * **/
    @POST
    public Response addUserAccount(UserAccount userAccount) {
        UserManager.addUserAccount(userAccount);
        Response res = new Response();
        res.setMessage("success");
        res.setStatus(Status.OK.getStatusCode());
        return res;
    }
    
    /**
     * get user account from local storage
     * @param user account id to be fetched
     * @return user account object if exist, null if not exist
     * **/
    @GET
    @Path("/{id}")
    public UserAccount getUserAccount(@PathParam("id") long id) {
        return UserManager.getUserAccount(id);
    }

    /**
     * get all user accounts from local storage
     * @return user accounts list if exist, empty list if it's empty
     * **/
    @GET
    public List<UserAccount> getAllUsers() {
        return UserManager.getAllUsers();
    }
    
    /**
     * Delete user account from local storage
     * @param user account id to be deleted
     * @return response holding success message and code(200) if user exist
     * @return response holding not found and code(500)
     * **/
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) {
        UserAccount account = UserManager.getUserAccount(id);
        Response res = new Response();
        if (account != null) {
            UserManager.removeUser(id);
            res.setMessage("success");
            res.setStatus(Status.OK.getStatusCode());
            
        } else {
            res.setMessage("user not found");
            res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        return res;
    }
}
