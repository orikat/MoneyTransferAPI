package com.revolut.webservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.revolut.models.Response;
import com.revolut.models.UserAccount;

@Path("/users")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class UserAccountResource {
    private static ConcurrentHashMap<Long, UserAccount> users = new ConcurrentHashMap<Long, UserAccount>();
    
    @POST
    public Response addUserAccount(UserAccount userAccount) {
        users.put(userAccount.getId(), userAccount);
        Response res = new Response();
        res.setMessage("success");
        res.setStatus(Status.OK.getStatusCode());
        return res;
    }
    
    @GET
    @Path("/{id}")
    public UserAccount getUserAccount(@PathParam("id") long id) {
        return users.get(id);
    }

    @GET
    public List<UserAccount> getAllUsers() {
        ArrayList<UserAccount> localUsers = new ArrayList<>();
        for (Entry<Long, UserAccount> userAccount : users.entrySet()) {
            localUsers.add(userAccount.getValue());
        }
        return localUsers;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) {
        UserAccount account = users.get(id);
        Response res = new Response();
        if (account != null) {
            users.remove(id);
            res.setMessage("success");
            res.setStatus(Status.OK.getStatusCode());
            
        } else {
            res.setMessage("not found");
            res.setStatus(Status.NOT_FOUND.getStatusCode());
        }
        return res;
    }
}
