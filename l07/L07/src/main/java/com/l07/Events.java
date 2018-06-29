/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.l07;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Brian Morehouse
 */
@Path("events")
public class Events {

    @GET
    @Path("showEvents")
    @Produces(MediaType.TEXT_HTML)
    public String getEvents() {
        //show allevents
        return "Got it!";
    }

    @POST
    public void createEvent(Events events) {
        //Create event entity 
        //createEventEntity(event);
    }

    @DELETE
    @Path("{id}")
    public void removeEvent(@PathParam("id") Short id) {
        //remove event entity from data store
        //removeEventEntity(id);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editDepartment(@PathParam("id") Short id, Events events) {
        //Updates department entity to data store
        //updateEventEntity(id, events);
    }

}
