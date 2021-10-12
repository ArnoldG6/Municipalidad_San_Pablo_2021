/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Restful;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import sfr.dao.PlanDAO;
import sfr.model.Plan;

/**
 *
 * @author jorsu
 */
@Path("/Plans")
public class Plans {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPlan(Plan plan) {
        try {
            PlanDAO.getInstance().add(plan);
        } catch (Exception ex) {
            System.out.println("No se pudo agregar el plan");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updatePlan(Plan plan) {
        try {
            PlanDAO.getInstance().update(plan);
        } catch (Exception ex) {
            System.out.println("No se pudo actualizar el plan");
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletePlan(Plan plan) {
        try {
            PlanDAO.getInstance().delete(plan);
        } catch (Exception ex) {
            System.out.println("No se pudo eliminar el plan");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Plan> getPlans() {
        return PlanDAO.getInstance().listAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Plan getPlanById(int id) {
        return PlanDAO.getInstance().searchById(id);
    }

}
