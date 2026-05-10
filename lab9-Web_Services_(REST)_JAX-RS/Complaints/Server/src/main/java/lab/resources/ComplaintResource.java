package lab.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lab.dto.ComplaintDTO;
import lab.services.ComplaintService;

import java.util.List;

@Path("complaints")
public class ComplaintResource {
    @Inject
    private ComplaintService service;


    @GET
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public List<ComplaintDTO> getAllComplaints(@QueryParam("status") String status) {
        return service.findAll(status);
    }

    @GET
    @Path("{id}")
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public ComplaintDTO getComplaint(@PathParam("id") Long id) {
        return service.find(id);
    }
    @POST
    @Consumes(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public void postComplaint(ComplaintDTO complaint) {
        service.create(complaint);
    }


    @PUT
    @Path("{id}")
    @Consumes(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public void putComplaint(@PathParam("id") Long id, ComplaintDTO
            complaint) {
        service.edit(complaint);
    }

    @DELETE
    @Path("{id}")
    public void deleteComplaint(@PathParam("id") Long id) {
        service.remove(service.find(id));
    }

    @GET
    @Path("{id}/status")
    @Produces(jakarta.ws.rs.core.MediaType.TEXT_PLAIN)
    public String checkStatus(@PathParam("id") Long id) {
        return service.find(id).getStatus();
    }

}
