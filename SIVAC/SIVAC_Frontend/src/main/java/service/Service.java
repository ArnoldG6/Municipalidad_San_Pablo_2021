/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import contants.Constants;
import contants.GetMonth;
import contants.PlusDate;
import java.util.Arrays;
import java.util.List;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import model.AdminFile;
import model.AreaMuni;
import model.EarlyVacations;
import model.Holidays;
import model.LaboraInhability;
import model.LicenseCGS;
import model.LicenseSGS;
import model.Message;
import model.Notifications;
import model.VacationDays;
import model.Vacations;
import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author jegon
 */
public class Service implements Serializable {

    private ResteasyClient client = null;

    public Service() {
        this.client = new ResteasyClientBuilder().build();
    }

    public AdminFile searchFileID(int id) {
        AdminFile result = null;
        result = client.target("http://localhost:8083/api/files").path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).get(AdminFile.class);
        return result;
    }

    public Notifications addNotification(Notifications notification) {
        Notifications saved = null;
        saved = client.target("http://localhost:8083/api/notifications").request()
                .post(Entity.entity(notification, MediaType.APPLICATION_JSON),
                        Notifications.class);
        System.out.println(saved.toString());
        return saved;
    }

    public Message addMessage(Message message) {
        Message saved = null;
        saved = client.target("http://localhost:8083/api/messages").request()
                .post(Entity.entity(message, MediaType.APPLICATION_JSON),
                        Message.class);
        return saved;
    }

    public List<AdminFile> allFiles() {
        List<AdminFile> files = null;
        files = Arrays.asList(client.target("http://localhost:8083/api/files").request(MediaType.APPLICATION_JSON)
                .get(AdminFile[].class));
        return files;
    }

    public List<Holidays> allHolidays() {
        List<Holidays> holidays = null;
        holidays = Arrays.asList(client.target("http://localhost:8083/api/holiday").request(MediaType.APPLICATION_JSON)
                .get(Holidays[].class));
        return holidays;
    }

    public List<Vacations> allVacations() {
        List<Vacations> vacation = null;
        vacation = Arrays.asList(client.target("http://localhost:8083/api/vacations").request(MediaType.APPLICATION_JSON)
                .get(Vacations[].class));
        return vacation;
    }

    public List<VacationDays> allDays() {
        List<VacationDays> days = null;
        days = Arrays.asList(client.target("http://localhost:8083/api/vacationdays").request(MediaType.APPLICATION_JSON)
                .get(VacationDays[].class));
        return days;
    }

    public List<AreaMuni> allAreas() {
        List<AreaMuni> areas = null;
        areas = Arrays.asList(client.target("http://localhost:8083/api/areas").request(MediaType.APPLICATION_JSON)
                .get(AreaMuni[].class));
        return areas;
    }

    public void addEarlyVacation(EarlyVacations early) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/early");
        EarlyVacations result = target.request().post(Entity.entity(early, MediaType.APPLICATION_JSON),
                EarlyVacations.class);
        System.out.println(result.toString());
    }

    public void deleteUser(int id) {
        boolean eliminated = client.target("http://localhost:8083/api/files").path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).delete(Boolean.class);
        if (eliminated) {
            System.out.println("Usuario borrado con éxito");
        } else {
            System.out.println("Error en la base de datos");
        }
    }

    public void deleteHoliday(int id) {
        boolean eliminated = client.target("http://localhost:8083/api/holiday").path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).delete(Boolean.class);
        if (eliminated) {
            System.out.println("Día feriado eliminado con éxito");
        } else {
            System.out.println("Error en la base de datos");
        }
    }

    public void deleteLicenseCGS(int id) {
        client.target("http://localhost:8083/api/licCGS").path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).delete(Boolean.class);
    }

    public void deleteInhability(int id) {
        client.target("http://localhost:8083/api/inhability").path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).delete(Boolean.class);
    }

    public void deleteLicenseSGS(int id) {
        client.target("http://localhost:8083/api/licSGS").path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).delete(Boolean.class);
    }

    public void deleteVacations(int id) {
        client.target("http://localhost:8083/api/vacations").path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).delete(Boolean.class);
    }

    public String getAdminFiles(String cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> admin = allFiles();
        r.append("<tbody>");
        if (cedula != null && !cedula.isEmpty()) {
            r.append(searchUserbyId(Integer.parseInt(cedula)));
        } else {
            for (AdminFile a : admin) {
                r.append("<tr>");
                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"7\" style = '%s'>", "text", "cedula",
                        "cedula", a.getID(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"9\" style = '%s'>", "text", "cedula",
                        "cedula", a.getName(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"15\" style = '%s'>", "text", "cedula",
                        "cedula", a.getLastname_1() + " " + a.getLastname_2(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\" style = '%s'>", "text", "cedula",
                        "cedula", a.getAreaMuni(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                //String fecha = PlusDate.plusDate(a.getAdmission_Date());
                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"10\" style = '%s'>", "text", "cedula",
                        "cedula", a.getEmployment(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\" style = '%s'>", "text", "cedula",
                        "cedula", a.getEmail(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"6\" style = '%s'>", "text", "cedula",
                        "cedula", a.getPhone(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px;\">");
                r.append(String.format("<form  method='%s'>", "POST"));
                r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d' "
                        + "style=\"background-color:transparent;\" onclick='%s' title=\"Editar\">",
                        "white", "submit", "numSol", "numSol", a.getID(), "this.form.action=\"../PassUser;\""));
                r.append(String.format("<img src='%s' width='%s'  >", "../images/update1.ico", "21px"));
                r.append("</button>");

                r.append(String.format("<button style='%s' "
                        + "onclick=\"return confirm('Segur@ de eliminar este usuario?')\" formaction='%s' "
                        + " value='%d' name=\"delete\" id=\"delete\" title=\"Eliminar\">",
                        "margin:2px;background-color:transparent;", "../DeleteUser;",
                        a.getID()));
                r.append(String.format("<img src='%s' width='%s'  >", "../images/trash1.ico", "21px"));
                r.append("</button>");

                r.append(String.format("<button style='%s' "
                        + "onclick=this.form.action=\"FilePDF.jsp;\" title='%s' value='%d' name='%s'>",
                        "background-color:transparent;", "Ver Historial", a.getID(), "historial"));
                r.append(String.format("<img src='%s' width='%s'  >", "../images/historial3.ico", "21px"));
                r.append("</button>");
                r.append("</form>");

                r.append("</th>");

                r.append("</tr");
                r.append("</br>");
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String getHolidays() {
        StringBuilder r = new StringBuilder();
        List<Holidays> holidays = allHolidays();

        for (Holidays a : holidays) {
            r.append("<tr>");

            r.append("<th style=\"width:5px;\">");
            r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"9\" style = '%s'>", "text", "dia",
                    "dia", a.getDay(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
            r.append("</th>");

            r.append("<th style=\"width:5px;\">");
            r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\" style = '%s'>", "text", "mes",
                    "mes", a.getMonth(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
            r.append("</th>");

            r.append("<th style=\"width:5px;\">");
            r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\" style = '%s'>", "text", "nombre",
                    "nombre", a.getName(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
            r.append("</th>");

            r.append("<th style=\"width:5px;\">");
            r.append(String.format("<form  method='%s'>", "POST"));
            r.append(String.format("<button style='%s' "
                    + "onclick=\"return confirm('Segur@ de eliminar este día feriado?')\" formaction='%s' "
                    + " value='%d' name=\"delete\" id=\"delete\" title=\"Eliminar\">",
                    "margin:2px;background-color:transparent;", "../DeleteHoliday;",
                    a.getIdHolidays()));
            r.append(String.format("<img src='%s' width='%s'  >", "../images/trash1.ico", "21px"));
            r.append("</button>");

            r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d' "
                    + "style=\"background-color:transparent;\" onclick='%s' title=\"Editar\">",
                    "white", "submit", "id", "id", a.getIdHolidays(), "this.form.action=\"../PassHoliday;\""));
            r.append(String.format("<img src='%s' width='%s'  >", "../images/update1.ico", "21px"));
            r.append("</button>");

            r.append("</form>");

            r.append("</th>");

            r.append("</tr");
            r.append("</br>");

        }
        return r.toString();
    }

    public String searchUserbyId(int cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> admin = allFiles();
        for (AdminFile adminF : admin) {
            if (adminF.getID() == cedula) {
                r.append("<tr>");
                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"7\" style = '%s'>", "text", "cedula",
                        "cedula", adminF.getID(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"9\" style = '%s'>", "text", "cedula",
                        "cedula", adminF.getName(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"15\" style = '%s'>", "text", "cedula",
                        "cedula", adminF.getLastname_1() + " " + adminF.getLastname_2(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\" style = '%s'>", "text", "cedula",
                        "cedula", adminF.getEmployment(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = PlusDate.plusDate(adminF.getAdmission_Date());
                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"10\" style = '%s'>", "text", "cedula",
                        "cedula", fecha, "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\" style = '%s'>", "text", "cedula",
                        "cedula", adminF.getEmail(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"6\" style = '%s'>", "text", "cedula",
                        "cedula", adminF.getPhone(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<form action='%s' method='%s'>", "PassUser", "POST"));
                r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d'>",
                        "white", "submit", "numSol", "numSol", adminF.getID()));
                r.append("Actualizar");
                r.append("</button>");
                r.append("</th>");

                r.append("</tr");
                r.append("</br>");
            }
        }
        return r.toString();
    }

    public String getPendingVacation(String cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> admin = allFiles();
        r.append("<tbody>");
        if (cedula != null && !cedula.isEmpty()) {
            r.append(searchPendingVacationByIdUser(Integer.parseInt(cedula)));
        } else {
            for (AdminFile a : admin) {
                r.append("<tr>");
                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"7\">", "text", "cedula",
                        "cedula", a.getID(), "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"9\">", "text", "cedula",
                        "cedula", a.getName(), "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"15\">", "text", "cedula",
                        "cedula", a.getLastname_1() + " " + a.getLastname_2(), "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\">", "text", "cedula",
                        "cedula", a.getEmployment(), "readonly"));
                r.append("</th>");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = PlusDate.plusDate(a.getAdmission_Date());
                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"10\">", "text", "cedula",
                        "cedula", fecha, "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' readonly = '%s' size=\"8\">", "text", "cedula",
                        "cedula", "readonly"));
                r.append("</th>");

                r.append("<th>");

                r.append("</th>");
                r.append("</tr");
                r.append("<td>");
                r.append("</br>");
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String searchPendingVacationByIdUser(int cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> admin = allFiles();
        for (AdminFile adminF : admin) {
            if (adminF.getID() == cedula) {
                r.append("<tr>");
                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"7\">", "text", "cedula",
                        "cedula", adminF.getID(), "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"9\">", "text", "cedula",
                        "cedula", adminF.getName(), "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"15\">", "text", "cedula",
                        "cedula", adminF.getLastname_1() + " " + adminF.getLastname_2(), "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"19\">", "text", "cedula",
                        "cedula", adminF.getEmployment(), "readonly"));
                r.append("</th>");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = PlusDate.plusDate(adminF.getAdmission_Date());
                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"10\">", "text", "cedula",
                        "cedula", fecha, "readonly"));
                r.append("</th>");

                r.append("<th style=\"width:5px\">");
                r.append(String.format("<input type='%s' id='%s' name='%s' readonly = '%s' size=\"8\">", "text", "cedula",
                        "cedula", "readonly"));
                r.append("</th>");

                r.append("<th>");

                r.append("</th>");
                r.append("</tr");
                r.append("<td>");
                r.append("</br>");
            }
        }
        return r.toString();
    }

    public void addCGS(LicenseCGS lic) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/licCGS");
        LicenseCGS cgs = target.request().post(Entity.entity(lic, MediaType.APPLICATION_JSON),
                LicenseCGS.class);
        System.out.println(cgs.toString());
        //LicenseCGS saved = null;
        /*saved = client.target("http://localhost:8083/api/licCGS").request(MediaType.APPLICATION_JSON);
                .post(Entity.entity(lic, MediaType.APPLICATION_JSON), LicenseCGS.class);*/
        //System.out.println(saved.toString());
    }

    public void updateFile(AdminFile adm) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/files");
        AdminFile ad = target.request().put(Entity.entity(adm, MediaType.APPLICATION_JSON),
                AdminFile.class);
        System.out.println(ad.toString());
    }

    public void updateVacationDay(VacationDays day) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/vacationdays");
        VacationDays result = target.request().put(Entity.entity(day, MediaType.APPLICATION_JSON),
                VacationDays.class);
        System.out.println(result.toString());
    }

    public void updateLicenseSGS(LicenseSGS sgs) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/licSGS");
        LicenseSGS updated = target.request().put(Entity.entity(sgs, MediaType.APPLICATION_JSON),
                LicenseSGS.class);
        System.out.println(updated.toString());
    }

    public void updateLicenseCGS(LicenseCGS cgs) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/licCGS");
        LicenseCGS updated = target.request().put(Entity.entity(cgs, MediaType.APPLICATION_JSON),
                LicenseCGS.class);
        System.out.println(updated.toString());
    }

    public void updateVacation(Vacations vac) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/vacations");
        Vacations updated = target.request().put(Entity.entity(vac, MediaType.APPLICATION_JSON),
                Vacations.class);
        System.out.println(updated.toString());
    }

    public void updateLaboral(LaboraInhability sgs) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/inhability");
        LaboraInhability updated = target.request().put(Entity.entity(sgs, MediaType.APPLICATION_JSON),
                LaboraInhability.class);
        System.out.println(updated.toString());
    }

    public void addSGS(LicenseSGS lic) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/licSGS");
        LicenseSGS cgs = target.request().post(Entity.entity(lic, MediaType.APPLICATION_JSON),
                LicenseSGS.class);
        System.out.println(cgs.toString());
    }

    public void addVacations(Vacations vacation) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/vacations");
        Vacations vac = target.request().post(Entity.entity(vacation, MediaType.APPLICATION_JSON),
                Vacations.class);
        System.out.println(vac.toString());
    }

    public void addAdmin(AdminFile admin) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/files");
        AdminFile ad = target.request().post(Entity.entity(admin, MediaType.APPLICATION_JSON),
                AdminFile.class);
        System.out.println(ad.toString());
    }

    public void addDisabilityProof(LaboraInhability proof) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/inhability");
        LaboraInhability dp = target.request().post(Entity.entity(proof, MediaType.APPLICATION_JSON),
                LaboraInhability.class);
        System.out.println(dp.toString());
    }

    public void addHolidays(Holidays holidays) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/holiday");
        Holidays ad = target.request().post(Entity.entity(holidays, MediaType.APPLICATION_JSON),
                Holidays.class);
        System.out.println(ad.toString());
    }

    public List<LicenseSGS> allSGS() {
        List<LicenseSGS> sgs = null;
        sgs = Arrays.asList(client.target("http://localhost:8083/api/licSGS").request(MediaType.APPLICATION_JSON)
                .get(LicenseSGS[].class));
        return sgs;
    }

    public String getLicensesSGS(int cedula) {
        StringBuilder r = new StringBuilder();
        List<LicenseSGS> licenses = allSGS();
        AdminFile boss = this.searchFileID(cedula);
        List<AdminFile> employees = this.allFiles();
        List<LicenseSGS> temp = new ArrayList<>();
        for (AdminFile file : employees) {
            if (file.getAreaMuni().equals(boss.getAreaMuni())) {
                temp = file.getSgs();
                r.append("<tbody>");
                for (LicenseSGS lic : licenses) {
                    for (LicenseSGS sgs : temp) {
                        if (sgs.getNumber_License() == lic.getNumber_License()) {
                            if (sgs.getStatus().equals("Pendiente")) {
                                r.append("<tr>");
                                r.append("<th>");
                                r.append(returnCedulaFuncionario(lic.getNumber_License()));
                                r.append("</th>");
                                r.append("<th>");
                                r.append(returnNombre(lic.getNumber_License()));
                                r.append("</th>");
                                r.append("<th>");
                                r.append(returnApellido(lic.getNumber_License()));
                                r.append("</th>");
                                r.append("<th>");
                                r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style='%s'>", "text",
                                        "num", "num", lic.getNumber_License(), "background-color: transparent; border: none; text-align: center"));
                                r.append("</th>");
                                r.append("<th>");
                                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style='%s'>", "text", "cedula",
                                        "cedula", lic.getStatus(), "readonly", "background-color: transparent; border: none; text-align: center"));
                                r.append("</th>");
                                r.append("<th>");
                                r.append(String.format("<form action='%s' method='%s'>", "../Pasar", "POST"));
                                r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d'>",
                                        "white", "submit", "numSol", "numSol", lic.getNumber_License()));
                                r.append("Ver detalles");
                                r.append("</button>");
                                r.append("</form>");
                                r.append("</th>");
                                r.append("</tr");
                                r.append("</br>");
                            }
                        }
                    }
                }
            }
            r.append("</tbody>");
        }
        return r.toString();
    }

    public String returnCedulaFuncionario(int numSGS) {
        List<AdminFile> files = allFiles();
        List<LicenseSGS> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getSgs();
            for (LicenseSGS sgs : temp) {
                if (sgs.getNumber_License() == numSGS) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly = '%s' style = '%s' >",
                            "text", "cedula", "cedula", file.getID(), "readonly", "background-color: transparent; outline: none; border: none"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnNombre(int numSGS) {
        List<AdminFile> files = allFiles();
        List<LicenseSGS> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getSgs();
            for (LicenseSGS sgs : temp) {
                if (sgs.getNumber_License() == numSGS) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%s'>",
                            "text", "nombre", "nombre", file.getName(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnApellido(int numSGS) {
        List<AdminFile> files = allFiles();
        List<LicenseSGS> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getSgs();
            for (LicenseSGS sgs : temp) {
                if (sgs.getNumber_License() == numSGS) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%s'>",
                            "text", "apellido", "apellido", file.getLastname_1(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String getBossLicenseSGS(int numSGS) {
        StringBuilder r = new StringBuilder();
        List<LicenseSGS> licenses = allSGS();
        for (LicenseSGS sgs : licenses) {
            if (sgs.getNumber_License() == numSGS) {
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Número Solicitud:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%d' readonly='%s'/>",
                        "num", "num", sgs.getNumber_License(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Categoría:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "categoriaSGS", "categoriaSGS", sgs.getCategory(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Inicial:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "start_Date", "start_Date", PlusDate.plusDate(sgs.getStart_Date()),
                        "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Final:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "final_Date", "final_Date", PlusDate.plusDate(sgs.getFinal_Date()),
                        "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Justificación:&nbsp;</td>");
                r.append("<td class=\"estilo\"> ");
                r.append(String.format("<textarea class='%s' name='%s'>",
                        "estilotextarea", "justification"));
                r.append(sgs.getJustification());
                r.append("</textarea>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Status de la licencia:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "status", sgs.getStatus(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Cambiar status de la licencia:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<select id = '%s' name = '%s'>", "cambiarStatus", "cambiarStatus"));
                r.append("<option size = '1'>Aprobada</option>");
                r.append("<option size = '1'>Denegada</option>");
                r.append("</select>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Documento Adjunto:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "file", "archivo", "", "readonly"));
                r.append("</td>");
                r.append("</tr>");
            }
        }
        return r.toString();
    }

    public String sendEmailLicenseSGS(int numSGS) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> files = allFiles();
        for (AdminFile file : files) {
            List<LicenseSGS> licensesSGS = file.getSgs();
            for (LicenseSGS sgs : licensesSGS) {
                if (sgs.getNumber_License() == numSGS) {
                    r.append("<form action=\"mailto:" + file.getEmail() + "\" method=\"post\" enctype=\"text/plain\" align=\"center\">");
                    r.append("<br><br>");
                    r.append("<tr>");
                    r.append("<td>");
                    r.append("E-mail:    ");
                    r.append(String.format("<input type=\"text\" name=\"mail\" placeholder='%s'><br><br>", "Digite su correo"));
                    r.append("</tr>");
                    r.append("</td>");
                    r.append("<tr>");
                    r.append("<td>");
                    r.append("<input type=\"submit\" value=\"Enviar\">           ");
                    r.append("&nbsp");
                    r.append("<input type=\"reset\" value=\"Cancelar\"   > <br><br>");
                    r.append("</tr>");
                    r.append("</td>");
                    r.append("</form>");
                }
            }
        }
        return r.toString();
    }

    public List<LicenseCGS> allCGS() {
        List<LicenseCGS> cgs = null;
        cgs = Arrays.asList(client.target("http://localhost:8083/api/licCGS").request(MediaType.APPLICATION_JSON)
                .get(LicenseCGS[].class));
        return cgs;
    }

    public String getLicensesCGS(int cedula) {
        StringBuilder r = new StringBuilder();
        List<LicenseCGS> licenses = allCGS();
        AdminFile boss = this.searchFileID(cedula);
        List<AdminFile> employees = this.allFiles();
        List<LicenseCGS> temp = new ArrayList<>();
        if (boss.getRole().getId_role() == 1) {
            r.append("<tbody>");
            for (AdminFile file : employees) {
                temp = file.getCgs();
                for (LicenseCGS cgs : temp) {
                    if (cgs.getCategory().equals("Invitaciones de Gobierno")) {
                        if (cgs.getStatus().equals("Pendiente")) {
                            r.append("<tr>");
                            r.append("<th>");
                            r.append(returnCedulaFuncionarioCGS(cgs.getNumber_License()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(returnNombreCGS(cgs.getNumber_License()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(returnApellidoCGS(cgs.getNumber_License()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                                    "num", "num", cgs.getNumber_License(), "background-color: transparent; outline: none; border: none; text-align: center"));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%S'>", "text", "cedula",
                                    "cedula", cgs.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(String.format("<form action='%s' method='%s'>", "../PasarCGS", "POST"));
                            r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d'>",
                                    "white", "submit", "numSol", "numSol", cgs.getNumber_License()));
                            r.append("Ver detalles");
                            r.append("</button>");
                            r.append("</form>");
                            r.append("</th>");
                            r.append("</tr");
                            r.append("</br>");
                        }
                    }
                }

            }
            r.append("</tbody>");
        } else {
            for (AdminFile file : employees) {
                if (file.getAreaMuni().equals(boss.getAreaMuni())) {
                    temp = file.getCgs();
                    r.append("<tbody>");
                    for (LicenseCGS lic : licenses) {
                        for (LicenseCGS cgs : temp) {
                            if (cgs.getNumber_License() == lic.getNumber_License()
                                    && !cgs.getCategory().equals("Invitaciones de Gobierno")) {
                                if (cgs.getStatus().equals("Pendiente")) {
                                    r.append("<tr>");
                                    r.append("<th>");
                                    r.append(returnCedulaFuncionarioCGS(lic.getNumber_License()));
                                    r.append("</th>");
                                    r.append("<th>");
                                    r.append(returnNombreCGS(lic.getNumber_License()));
                                    r.append("</th>");
                                    r.append("<th>");
                                    r.append(returnApellidoCGS(lic.getNumber_License()));
                                    r.append("</th>");
                                    r.append("<th>");
                                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                                            "num", "num", lic.getNumber_License(), "background-color: transparent; outline: none; border: none; text-align: center"));
                                    r.append("</th>");
                                    r.append("<th>");
                                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%S'>", "text", "cedula",
                                            "cedula", lic.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                                    r.append("</th>");
                                    r.append("<th>");
                                    r.append(String.format("<form action='%s' method='%s'>", "../PasarCGS", "POST"));
                                    r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d'>",
                                            "white", "submit", "numSol", "numSol", lic.getNumber_License()));
                                    r.append("Ver detalles");
                                    r.append("</button>");
                                    r.append("</form>");
                                    r.append("</th>");
                                    r.append("</tr");
                                    r.append("</br>");
                                }
                            }
                        }
                    }
                }
                r.append("</tbody>");
            }
        }
        return r.toString();
    }

    public String getVacationDays(int cedula) {
        StringBuilder r = new StringBuilder();
        List<VacationDays> temp = new ArrayList<>();
        AdminFile file = searchFileID(cedula);
        List<VacationDays> days = new ArrayList<>();
        List<Vacations> vacations = new ArrayList<>();

        temp = file.getDays();

        for (VacationDays day : temp) {
            if (day.getStatus().equals("Pendiente")) {
                r.append("<tr>");
                r.append(String.format("<form method='%s'>",  "POST"));
                r.append("<th>");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                        "ced", "ced", file.getID(),
                        "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");
                r.append("<th>");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly style = '%s'>", "text",
                        "nombre", "nombre", file.getName(),
                        "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");
                r.append("<th>");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly style = '%s'>", "text",
                        "apel", "apel", file.getLastname_1() + " " + file.getLastname_2(),
                        "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");
                r.append("<th>");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly style = '%s'>", "text",
                        "num", "num", PlusDate.plusDate(day.getDays()), "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");
                r.append("<th>");
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%S'>", "text", "status",
                        "status", day.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                r.append("</th>");
                r.append("<th>");
                
                r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d' title='%s' "
                        + " formaction='%s'>",
                        "white", "submit", "aprobar", "aprobar", day.getId_day(), "Aprobar", "../AprobarDia"));
                r.append(String.format("<img src='%s' width='%s'  >", "../images/check.ico", "21px"));
                r.append("</button>");
                r.append("</th>");

                r.append("<th>");
                r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d' title='%s' "
                        + "formaction='%s'>",
                        "white", "submit", "denegar", "denegar", day.getId_day(), "Denegar", "../DenegarDia"));
                r.append(String.format("<img src='%s' width='%s'  >", "../images/no.ico", "21px"));
                r.append("</button>");
                r.append("</form>");
                r.append("</th>");

                r.append("</tr");
                r.append("</br>");
            }
        }

        return r.toString();
    }

    public String returnCedulaFuncionarioCGS(int numCGS) {
        List<AdminFile> files = allFiles();
        List<LicenseCGS> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getCgs();
            for (LicenseCGS cgs : temp) {
                if (cgs.getNumber_License() == numCGS) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly = '%s' style='%s'>",
                            "text", "cedula", "cedula", file.getID(), "readonly", "background-color: transparent; outline: none; border: none"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnNombreCGS(int numCGS) {
        List<AdminFile> files = allFiles();
        List<LicenseCGS> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getCgs();
            for (LicenseCGS cgs : temp) {
                if (cgs.getNumber_License() == numCGS) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style='%s'>",
                            "text", "nombre", "nombre", file.getName(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnApellidoCGS(int numCGS) {
        List<AdminFile> files = allFiles();
        List<LicenseCGS> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getCgs();
            for (LicenseCGS cgs : temp) {
                if (cgs.getNumber_License() == numCGS) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%s'>",
                            "text", "apellido", "apellido", file.getLastname_1(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String getBossLicenseCGS(int numCGS) throws IOException {
        StringBuilder r = new StringBuilder();
        List<LicenseCGS> licenses = allCGS();
        for (LicenseCGS cgs : licenses) {
            if (cgs.getNumber_License() == numCGS) {
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Número Solicitud:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%d' readonly='%s'/>",
                        "num", "num", cgs.getNumber_License(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Categoría:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "categoriaCGS", "categoriaCGS", cgs.getCategory(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Inicial:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "start_Date", "start_Date", PlusDate.plusDate(cgs.getStart_Date()), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Final:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "final_Date", "final_Date", PlusDate.plusDate(cgs.getFinal_Date()), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Justificación:&nbsp;</td>");
                r.append("<td class=\"estilo\"> ");
                r.append(String.format("<textarea class='%s' name='%s'>",
                        "estilotextarea", "justification"));
                r.append(cgs.getJustification());
                r.append("</textarea>");
                r.append("</td>");
                r.append("</tr>");

                try {
                    InputStream image = new ByteArrayInputStream(cgs.getPDF());

                    if (Constants.validarPDF(image.toString())) {
                        r.append("<tr>");
                        r.append("<td class=\"etiqueta\">PDF:&nbsp;</td>");
                        r.append("<td>");
                        r.append(String.format("<iframe src='%s' width=\"100%\" height=\"300px\">\n "
                                + "    </iframe>", image));
                        r.append("</td>");
                        r.append("</tr>");
                    }
                } catch (NullPointerException e) {

                }

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Status de la licencia:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "status", cgs.getStatus(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Cambiar status de la licencia:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<select id = '%s' name = '%s'>", "cambiarStatus", "cambiarStatus"));
                r.append("<option size = '1'>Aprobada</option>");
                r.append("<option size = '1'>Denegada</option>");
                r.append("</select>");
                r.append("</td>");
                r.append("</tr>");

            }
        }
        return r.toString();
    }

    public String sendEmailLicenseCGS(int numCGS) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> files = allFiles();
        for (AdminFile file : files) {
            List<LicenseCGS> licensesCGS = file.getCgs();
            for (LicenseCGS cgs : licensesCGS) {
                if (cgs.getNumber_License() == numCGS) {

                    r.append("<form class=\"denegate\" action=\"mailto:" + file.getEmail() + "\" method=\"post\" enctype=\"text/plain\" align=\"center\">");
                    r.append("<br><br>");
                    r.append("<tr>");
                    r.append("<td>");
                    r.append("E-mail:     ");
                    r.append(String.format("<input type=\"text\" name=\"mail\" placeholder='%s'><br><br>", "Digite su correo"));
                    r.append("</td>");
                    r.append("</tr>");
                    r.append("<tr>");
                    r.append("<td>");
                    r.append("<input type=\"submit\" value=\"Enviar\">         ");
                    r.append("&nbsp");
                    r.append("<input type=\"reset\" value=\"Cancelar\"    > <br><br>");
                    r.append("</td>");
                    r.append("</tr>");
                    r.append("</form>");
                }
            }
        }
        return r.toString();
    }

    public List<LaboraInhability> allInhability() {
        List<LaboraInhability> li = null;
        li = Arrays.asList(client.target("http://localhost:8083/api/inhability").request(MediaType.APPLICATION_JSON)
                .get(LaboraInhability[].class));
        return li;
    }

    public String getLaboral(int idboss) {
        StringBuilder r = new StringBuilder();
        List<LaboraInhability> laboral = allInhability();
        AdminFile boss = this.searchFileID(idboss);
        List<AdminFile> employees = this.allFiles();
        List<LaboraInhability> temp = new ArrayList<>();
        r.append("<tbody>");
        for (AdminFile file : employees) {
            if (file.getAreaMuni().equals(boss.getAreaMuni())) {
                temp = file.getInhability();
                for (LaboraInhability lic : laboral) {
                    for (LaboraInhability tmp : temp) {
                        if (tmp.getNumber_Inhability() == lic.getNumber_Inhability()) {
                            r.append("<tr>");
                            r.append("<th>");
                            r.append(returnCedulaLab(lic.getNumber_Inhability()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(returnNombreLab(lic.getNumber_Inhability()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(returnApellidoLab(lic.getNumber_Inhability()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                                    "num", "num", lic.getNumber_Inhability(), "background-color: transparent; outline: none; border: none; text-align: center"));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(String.format("<form action='%s' method='%s'>", "../GetProof", "POST"));
                            r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d'>",
                                    "white", "submit", "numSol", "numSol", lic.getNumber_Inhability()));
                            r.append("Ver Incapacidad");
                            r.append("</button>");
                            r.append("</form>");
                            r.append("</th>");
                            r.append("</tr");
                            r.append("</br>");
                        }
                    }
                }
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String getLaboralRh() {
        StringBuilder r = new StringBuilder();
        List<LaboraInhability> laboralRh = allInhability();
        r.append("<tbody>");
        for (LaboraInhability lic : laboralRh) {
            r.append("<tr>");
            r.append("<th>");
            r.append(returnCedulaLab(lic.getNumber_Inhability()));
            r.append("</th>");
            r.append("<th>");
            r.append(returnNombreLab(lic.getNumber_Inhability()));
            r.append("</th>");
            r.append("<th>");
            r.append(returnApellidoLab(lic.getNumber_Inhability()));
            r.append("</th>");
            r.append("<th>");
            r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                    "num", "num", lic.getNumber_Inhability(), "background-color: transparent; outline: none; border: none; text-align: center"));
            r.append("</th>");
            r.append("<th>");
            r.append(String.format("<form action='%s' method='%s'>", "../GetProofRH", "POST"));
            r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d'>",
                    "white", "submit", "numSol", "numSol", lic.getNumber_Inhability()));
            r.append("Ver Incapacidad");
            r.append("</button>");
            r.append("</form>");
            r.append("</th>");
            r.append("</tr");
            r.append("</br>");
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String getLaboralEM() {
        StringBuilder r = new StringBuilder();
        List<LaboraInhability> laboralEM = allInhability();
        r.append("<tbody>");
        for (LaboraInhability lic : laboralEM) {
            r.append("<tr>");
            r.append("<th>");
            r.append(returnCedulaLab(lic.getNumber_Inhability()));
            r.append("</th>");
            r.append("<th>");
            r.append(returnNombreLab(lic.getNumber_Inhability()));
            r.append("</th>");
            r.append("<th>");
            r.append(returnApellidoLab(lic.getNumber_Inhability()));
            r.append("</th>");
            r.append("<th>");
            r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                    "num", "num", lic.getNumber_Inhability(), "background-color: transparent; outline: none; border: none; text-align: center"));
            r.append("</th>");
            r.append("<th>");
            r.append(String.format("<form action='%s' method='%s'>", "../ViewLaboralEm", "POST"));
            r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d'>",
                    "white", "submit", "numSol", "numSol", lic.getNumber_Inhability()));
            r.append("Ver Incapacidad");
            r.append("</button>");
            r.append("</form>");
            r.append("</th>");
            r.append("</tr");
            r.append("</br>");
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String returnCedulaLab(int lab) {
        List<AdminFile> files = allFiles();
        List<LaboraInhability> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getInhability();
            for (LaboraInhability sgs : temp) {
                if (sgs.getNumber_Inhability() == lab) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly = '%s' style = '%s'>",
                            "text", "cedula", "cedula", file.getID(), "readonly", "background-color: transparent; outline: none; border: none"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnNombreLab(int lab) {
        List<AdminFile> files = allFiles();
        List<LaboraInhability> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getInhability();
            for (LaboraInhability sgs : temp) {
                if (sgs.getNumber_Inhability() == lab) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style ='%s'>",
                            "text", "nombre", "nombre", file.getName(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnApellidoLab(int lab) {
        List<AdminFile> files = allFiles();
        List<LaboraInhability> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getInhability();
            for (LaboraInhability sgs : temp) {
                if (sgs.getNumber_Inhability() == lab) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%s'>",
                            "text", "apellido", "apellido", file.getLastname_1(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String getBossLaboral(int lab) {
        StringBuilder r = new StringBuilder();
        List<LaboraInhability> licenses = allInhability();
        for (LaboraInhability cgs : licenses) {
            if (cgs.getNumber_Inhability() == lab) {
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Número de solicitud de incapacidad:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%d' readonly='%s'/>",
                        "num", "num", cgs.getNumber_Inhability(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Status de la licencia:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "status", "Aprobada", "readonly"));
                r.append("</td>");
                r.append("</tr>");

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(cgs.getMainDate());
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha enviada:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "envio", fecha, "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Documento Adjunto:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<p><img src=\"../CargaComprobante?numberInhability=%d\" /></p>",
                        cgs.getNumber_Inhability()));
                r.append("</td>");
                r.append("</tr>");
            }
        }
        return r.toString();
    }

    public String getEmployeeLaboral(int lab) {
        StringBuilder r = new StringBuilder();
        List<LaboraInhability> licenses = allInhability();
        for (LaboraInhability cgs : licenses) {
            if (cgs.getNumber_Inhability() == lab) {
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Número de solicitud de incapacidad:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%d' readonly='%s'/>",
                        "num", "num", cgs.getNumber_Inhability(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Status de la licencia:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "status", "Aprobada", "readonly"));
                r.append("</td>");
                r.append("</tr>");

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(cgs.getMainDate());
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha enviada:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "envio", fecha, "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Documento Adjunto:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<p><img src=\"../CargaComprobante?numberInhability=%d\" /></p>",
                        cgs.getNumber_Inhability()));
                r.append("</td>");
                r.append("</tr>");
            }
        }
        return r.toString();
    }

    public LaboraInhability searchByRequestLabora(int numberSolicitud) {
        List<LaboraInhability> labora = allInhability();
        for (LaboraInhability inha : labora) {
            if (inha.getNumber_Inhability() == numberSolicitud) {
                return inha;
            }
        }
        return null;
    }

    public String getInfoActUser(int id) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> files = allFiles();
        //r.append("<tbody>");
        for (AdminFile adm : files) {
            if (adm.getID() == id) {
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Nombre:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s'>",
                        "name", "name", adm.getName()));
                r.append("</td>");
                r.append("</tr>");
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Primer apellido:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s'>",
                        "lastname_1", "lastname_1", adm.getLastname_1()));
                r.append("</td>");
                r.append("</tr>");
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Segundo apellido:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s'>",
                        "lastname_2", "lastname_2", adm.getLastname_2()));
                r.append("</td>");
                r.append("</tr>");
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Área de trabajo&nbsp;");
                r.append("</td>");
                r.append("<td>");
                r.append("<class=\"campo\">");
                r.append("<select id=\"area\" name=\"area\">");
                r.append(returnAreas());
                r.append("</select>");
                r.append("</td>");
                r.append("</tr>");
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Puesto:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s'>",
                        "employment", "employment", adm.getEmployment()));
                r.append("</td>");
                r.append("</tr>");
                r.append("<tr>");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = PlusDate.plusDate(adm.getAdmission_Date());
                r.append("<td class=\"etiqueta\">Fecha de ingreso:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s' type=\"date\">",
                        "admission_Date", "admission_Date", fecha));
                r.append("</td>");
                r.append("</tr>");
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Teléfono:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%d'>",
                        "phone", "phone", adm.getPhone()));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Permiso para vacaciones adelantadas:");
                r.append("</td>");
                r.append("<td>");
                r.append("<select id = \"early\" name = \"early\">");

                r.append(String.format("<option value='%s'>%s</option>", "0", "Denegado"));
                r.append(String.format("<option value='%s'>%s</option>", "1", "Aprobado"));
                r.append("</select>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Correo institucional:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s'>",
                        "email", "email", adm.getEmail()));
                r.append("</td>");
                r.append("</tr>");
                
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Anualidad:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%d'>",
                        "public_years", "public_years", adm.getPublic_years()));
                r.append("</td>");
                r.append("</tr>");
                
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Salario:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s'>",
                        "salary", "salary", adm.getSalary()));
                r.append("</td>");
                r.append("</tr>");
                
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Vacaciones:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id='%s' name='%s' value='%s'>",
                        "holidays", "holidays", adm.getHolidays()));
                r.append("</td>");
                r.append("</tr>");
                
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Nivel Jerárquico:");
                r.append("</td>");
                r.append("<td>");
                r.append("<select id = \"Role_id\" name = \"Role_id\">");

                r.append(String.format("<option value='%s'>%s</option>", "1", "Alcalde"));
                r.append(String.format("<option value='%s'>%s</option>", "2", "Director"));
                r.append(String.format("<option value='%s'>%s</option>", "3", "Jefe de Departamento"));
                r.append(String.format("<option value='%s'>%s</option>", "4", "Encargado de Unidad"));
                r.append(String.format("<option value='%s'>%s</option>", "5", "Funcionario"));
                r.append("</select>");
                r.append("</td>");
                r.append("</tr");
            }
        }
        //r.append("</tbody>");
        return r.toString();
    }

    public String returnCedula(int numID) {
        List<AdminFile> files = allFiles();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            if (file.getID() == numID) {
                r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly = '%s' style = '%s'>",
                        "hidden", "cedula", "cedula", file.getID(), "readonly", "background-color: transparent;"
                                + "outline: none; border: none; text-align: center"));
                break;
            }
        }
        return r.toString();
    }

    public String getVacation(String idboss) {
        StringBuilder r = new StringBuilder();
        List<Vacations> vacation = allVacations();
        AdminFile boss = this.searchFileID(Integer.parseInt(idboss));
        List<AdminFile> employees = this.allFiles();
        List<Vacations> temp = new ArrayList<>();
        r.append("<tbody>");
        for (AdminFile file : employees) {
            if (file.getAreaMuni().equals(boss.getAreaMuni())) {
                temp = file.getVacations();
                for (Vacations vac : vacation) {
                    for (Vacations tmp : temp) {
                        if (tmp.getVacations_ID() == vac.getVacations_ID()) {
                            if (tmp.getStatus().equals("Pendiente")) {
                                r.append("<tr>");
                                r.append("<th>");
                                r.append(returnIdVacation(vac.getVacations_ID()));
                                r.append("</th>");
                                r.append("<th>");
                                r.append(returnNombreVacation(vac.getVacations_ID()));
                                r.append("</th>");
                                r.append("<th>");
                                r.append(returnApellidoVacation(vac.getVacations_ID()));
                                r.append("</th>");

                                r.append("<th>");
                                r.append(String.format("<form action='%s' method='%s'>", "../PasarVacation", "POST"));
                                r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d' style = '%s'>",
                                        "white", "submit", "idVac", "idVac", file.getID(), "text-align: center"));
                                r.append(String.format("<img src='%s' width='%s'  >", "../images/lupa.ico", "21px"));
                                r.append("</button>");
                                r.append("</form>");
                                r.append("</th>");

                                r.append("<th>");
                                r.append(String.format("<form action='%s' method='%s'>", "../AcceptVacation", "POST"));
                                r.append(String.format("<button style='%s' "
                                        + "onclick=\"return confirm('Segur@ de marcar como revisado?')\"  "
                                        + " value='%d' name=\"accept\" id=\"accept\" title=\"Revisado\">",
                                        "margin:2px;background-color:transparent;",
                                        tmp.getVacations_ID()));
                                r.append(String.format("<img src='%s' width='%s'  >", "../images/check.ico", "21px"));
                                r.append("</button>");
                                r.append("</form>");
                                r.append("</th>");

                                r.append("</tr");
                                r.append("</br>");
                            }
                        }
                    }
                }
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String getVacaStatus(String idboss) {
        StringBuilder r = new StringBuilder();
        List<Vacations> vacation = allVacations();
        AdminFile boss = this.searchFileID(Integer.parseInt(idboss));
        List<AdminFile> employees = this.allFiles();
        List<Vacations> temp = new ArrayList<>();
        r.append("<tbody>");
        for (AdminFile file : employees) {
            if (file.getAreaMuni().equals(boss.getAreaMuni())) {
                temp = file.getVacations();
                for (Vacations vac : vacation) {
                    for (Vacations tmp : temp) {
                        if (tmp.getVacations_ID() == vac.getVacations_ID() && vac.getStatus().equals("Aprobada") || vac.getStatus().equals("Denegada")) {
                            r.append("<tr>");
                            r.append("<th>");
                            r.append(returnIdVacation(vac.getVacations_ID()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(returnNombreVacation(vac.getVacations_ID()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(returnApellidoVacation(vac.getVacations_ID()));
                            r.append("</th>");
                            r.append("<th>");
                            r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"8\" style = '%s'>", "text", "cedula",
                                    "cedula", vac.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                            r.append("</th>");
                            r.append("</tr");
                            r.append("</br>");
                        }
                    }
                }
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String searchUserbyIdVacation(int cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> admin = allFiles();
        for (AdminFile adminF : admin) {
            if (adminF.getID() == cedula) {
                List<Vacations> vacations = adminF.getVacations();
                for (Vacations vac : vacations) {
                    r.append("<tr>");
                    r.append("<th>");
                    r.append(returnIdVacation(vac.getVacations_ID()));
                    r.append("</th>");
                    r.append("<th>");
                    r.append(returnNombreVacation(vac.getVacations_ID()));
                    r.append("</th>");
                    r.append("<th>");
                    r.append(returnApellidoVacation(vac.getVacations_ID()));
                    r.append("</th>");
                    r.append("<th>");
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"8\" style = '%s'>", "text", "cedula",
                            "cedula", vac.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    r.append("</th>");
                    r.append("<th>");
                    r.append(String.format("<form action='%s' method='%s'>", "PasarVacation", "POST"));
                    r.append(String.format("<button color='%s' type='%s' name='%s' id='%s' value='%d' style = '%s'>",
                            "white", "submit", "idVac", "idVac", vac.getVacations_ID(), "text-align: center"));
                    r.append("Ver detalles");
                    r.append("</button>");
                    r.append("</form>");
                    r.append("</th>");
                    r.append("</tr");
                    r.append("</br>");
                }
            }
        }
        return r.toString();
    }

    public String returnIdVacation(int id) {
        List<AdminFile> files = allFiles();
        List<Vacations> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getVacations();
            for (Vacations vac : temp) {
                if (vac.getVacations_ID() == id) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly = '%s' size=\"15\" style = '%s'>",
                            "text", "cedula", "cedula", file.getID(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnNombreVacation(int id) {
        List<AdminFile> files = allFiles();
        List<Vacations> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getVacations();
            for (Vacations vac : temp) {
                if (vac.getVacations_ID() == id) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"9\" style = '%s'>",
                            "text", "nombre", "nombre", file.getName(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String returnApellidoVacation(int id) {
        List<AdminFile> files = allFiles();
        List<Vacations> temp = new ArrayList<>();
        StringBuilder r = new StringBuilder();
        for (AdminFile file : files) {
            temp = file.getVacations();
            for (Vacations vac : temp) {
                if (vac.getVacations_ID() == id) {
                    r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' size=\"15\" style = '%s'>",
                            "text", "apellido", "apellido", file.getLastname_1() + " " + file.getLastname_2(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                    break;
                }
            }
        }
        return r.toString();
    }

    public String getBossVacation(int id) {
        StringBuilder r = new StringBuilder();
        List<Vacations> vacation = allVacations();
        for (Vacations vac : vacation) {
            if (vac.getVacations_ID() == id) {
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Número Solicitud:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%d' readonly='%s'/>",
                        "num", "num", vac.getVacations_ID(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Inicial:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "start_Date", "start_Date", PlusDate.plusDate(vac.getStart_Date()), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Final:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "final_Date", "final_Date", PlusDate.plusDate(vac.getFinal_Date()), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Status de la vacación:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "status", vac.getStatus(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Cambiar status de la vacación:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<select id = '%s' name = '%s'>", "cambiarStatus", "cambiarStatus"));
                r.append("<option size = '1'>Aprobada</option>");
                r.append("<option size = '1'>Denegada</option>");
                r.append("</select>");
                r.append("</td>");
                r.append("</tr>");

            }
        }
        return r.toString();
    }

    public String getEmployeeVacation(int id) {
        StringBuilder r = new StringBuilder();
        List<Vacations> vacation = allVacations();
        for (Vacations vac : vacation) {
            if (vac.getVacations_ID() == id) {
                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Número Solicitud:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%d' readonly='%s'/>",
                        "num", "num", vac.getVacations_ID(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Inicial:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "start_Date", "start_Date", PlusDate.plusDate(vac.getStart_Date()), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Fecha Final:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input name='%s' id='%s' value='%s' readonly='%s'/>",
                        "final_Date", "final_Date", PlusDate.plusDate(vac.getFinal_Date()), "readonly"));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Status de la vacación:&nbsp;</td>");
                r.append("<td class=\"campo\"> ");
                r.append(String.format("<input type='%s' name='%s' value='%s' readonly='%s'/>",
                        "text", "status", vac.getStatus(), "readonly"));
                r.append("</td>");
                r.append("</tr>");

            }
        }
        return r.toString();
    }

    public String returnAreas() {
        StringBuilder r = new StringBuilder();
        List<AreaMuni> areas = this.allAreas();
        for (AreaMuni area : areas) {
            r.append("<option>");
            r.append(area.getDescription());
            r.append("</option>");
        }
        return r.toString();
    }

    public String userAccess(int id) {
        AdminFile file = searchFileID(id);
        if (file != null) {
            if (file.getRole().getId_role() == 4 && file.getAreaMuni().equals("RRHH")
                    || file.getRole().getId_role() == 4 && file.getAreaMuni().equals("Tecnologia de Informacion")) { //Cambiar despúes
                return "../RRHHMain";
            }

            if (file.getRole().getId_role() == 4 || file.getRole().getId_role() == 3
                    || file.getRole().getId_role() == 2 || file.getRole().getId_role() == 1) { //Cambiar despúes
                return "../BossMain";
            }

            if (file.getRole().getId_role() == 5) {
                return "../EmployeeMain";
            }
        }
        return "../error";
    }

    public String disabilityProofAccess(int id) {
        StringBuilder r = new StringBuilder();
        AdminFile file = this.searchFileID(id);
        if (file.getRole().getId_role() == 1 && file.getAreaMuni().equals("Tecnologia de Informacion")
                || file.getAreaMuni().equals("RRHH")) { //Sujeto a cambios

            r.append("<tr>");
            r.append("<td class=\"etiqueta\">Cédula:</td>");
            r.append("<td class=\"campo\"><input type=\"text\" name=\"ced\" id=\"ced\"></td>");
            r.append("</tr>");
        } else {

            r.append("<tr>");
            r.append("<td class=\"etiqueta\">Cedula:</td>");
            r.append(String.format("<td class=''%s><input type='%s' name='%s' "
                    + "id=''%s value='%d' readonly='%s' style='%s'></td>",
                    "campo", "text", "ced", "ced", file.getID(),
                    "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
            r.append("</tr>");
        }
        return r.toString();
    }

    public String licenseAccess(int id) {
        StringBuilder r = new StringBuilder();
        AdminFile file = this.searchFileID(id);
        if (file.getRole().getId_role() == 4 && file.getAreaMuni().equals("Tecnologia de Informacion")
                || file.getAreaMuni().equals("RRHH")) { //Sujeto a cambios
            r.append("<tr>");
            r.append("<td class=\"etiqueta\">Cédula&nbsp;</td>");
            r.append("<td class=\"campo\"><input type=\"text\" name=\"cedula\" id=\"cedula\"></td>");
            r.append("</tr>");
        } else {
            r.append("<tr>");
            r.append("<td class=\"etiqueta\">Cédula&nbsp;</td>");
            r.append(String.format("<td class=''%s><input type='%s' name='%s' "
                    + "id=''%s value='%d' readonly='%s' style='%s'></td>",
                    "campo", "text", "cedula", "cedula", file.getID(),
                    "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
            r.append("</tr>");
        }
        return r.toString();
    }

    public int cedulaJefe(int cedula) {
        AdminFile file = searchFileID(cedula);
        List<AdminFile> files = this.allFiles();
        for (AdminFile result : files) {
            if (result.getAreaMuni().equals(file.getAreaMuni()) && result.getRole().getId_role() == (file.getRole().getId_role() - 1)) {
                switch (result.getRole().getId_role()) {

                    case 4:
                        if ((file.getAreaMuni().equals("RRHH") && file.getRole().getId_role() == 4)
                                || (file.getAreaMuni().equals("Tecnologia de Informacion") && file.getRole().getId_role() == 4)) { //Sujeto a cambios
                            return getCedulaAlcalde();
                        } else {
                            return result.getID();
                        }
                    case 3:
                        return result.getID();
                    case 2:
                        return result.getID();

                }
            }
        }

        return 0;
    }

    public int getCedulaAlcalde() {
        List<AdminFile> files = this.allFiles();
        for (AdminFile result : files) {
            if (result.getRole().getId_role() == 1) {
                return result.getID();
            }
        }
        return 0;
    }

    public int getCedulaFuncionario(int jefe) {
        AdminFile file = searchFileID(jefe);
        List<AdminFile> files = this.allFiles();
        for (AdminFile result : files) {
            if (result.getAreaMuni().equals(file.getAreaMuni())) {
                switch (result.getRole().getId_role()) {
                    case 5:
                        return result.getID();
                    case 4:

                        return result.getID();

                    case 3:
                        return result.getID();
                    case 2:
                        return result.getID();

                }
            }
        }
        return 0;
    }

    public String responseVacation(int cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> employees = this.allFiles();
        for (AdminFile file : employees) {
            if (file.getID() == cedula) {
                List<Vacations> vacations = file.getVacations();
                r.append("<tbody>");
                for (Vacations vac : vacations) {
                    if (vac.getStatus().equals("Aprobada") || vac.getStatus().equals("Denegada")) {
                        r.append("<tr>");
                        r.append("<th>");
                        r.append(returnIdVacation(vac.getVacations_ID()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(returnNombreVacation(vac.getVacations_ID()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(returnApellidoVacation(vac.getVacations_ID()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                                "num", "num", vac.getVacations_ID(), "background-color: transparent; outline: none; border: none; text-align: center"));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%S'>", "text", "cedula",
                                "cedula", vac.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                        r.append("</th>");
                        r.append("</tr");
                        r.append("</br>");
                    }
                }
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String responseCGS(int cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> employees = this.allFiles();
        for (AdminFile file : employees) {
            if (file.getID() == cedula) {
                List<LicenseCGS> licenses = file.getCgs();
                r.append("<tbody>");
                for (LicenseCGS cgs : licenses) {
                    if (cgs.getStatus().equals("Aprobada") || cgs.getStatus().equals("Denegada")) {
                        r.append("<tr>");
                        r.append("<th>");
                        r.append(returnCedulaFuncionarioCGS(cgs.getNumber_License()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(returnNombreCGS(cgs.getNumber_License()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(returnApellidoCGS(cgs.getNumber_License()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                                "num", "num", cgs.getNumber_License(), "background-color: transparent; outline: none; border: none; text-align: center"));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%S'>", "text", "cedula",
                                "cedula", cgs.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                        r.append("</th>");
                        r.append("</tr");
                        r.append("</br>");
                    }
                }
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String responseSGS(int cedula) {
        StringBuilder r = new StringBuilder();
        List<AdminFile> employees = this.allFiles();
        for (AdminFile file : employees) {
            if (file.getID() == cedula) {
                List<LicenseSGS> licenses = file.getSgs();
                r.append("<tbody>");
                for (LicenseSGS sgs : licenses) {
                    if (sgs.getStatus().equals("Aprobada") || sgs.getStatus().equals("Denegada")) {
                        r.append("<tr>");
                        r.append("<th>");
                        r.append(returnCedulaFuncionario(sgs.getNumber_License()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(returnNombre(sgs.getNumber_License()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(returnApellido(sgs.getNumber_License()));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(String.format("<input type='%s' id='%s' name='%s' value='%d' readonly style = '%s'>", "text",
                                "num", "num", sgs.getNumber_License(), "background-color: transparent; outline: none; border: none; text-align: center"));
                        r.append("</th>");
                        r.append("<th>");
                        r.append(String.format("<input type='%s' id='%s' name='%s' value='%s' readonly = '%s' style = '%S'>", "text", "cedula",
                                "cedula", sgs.getStatus(), "readonly", "background-color: transparent; outline: none; border: none; text-align: center"));
                        r.append("</th>");
                        r.append("</tr");
                        r.append("</br>");
                    }
                }
            }
        }
        r.append("</tbody>");
        return r.toString();
    }

    public String getFileEmployee(int cedula) {
        StringBuilder r = new StringBuilder();
        AdminFile file = this.searchFileID(cedula);

        if (file != null) {
            r.append("<table  border='1'>");
            r.append("<tr>");
            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append(String.format("<label id='%s'>", "ced"));
            r.append(String.format("Cedula: %s", cedula));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");
            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Nombre Funcionario: %s", file.getName() + " " + file.getLastname_1() + " " + file.getLastname_2()));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");
            r.append("</tr>");

            r.append("<tr>");
            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Área Laboral: %s", file.getAreaMuni()));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");

            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Puesto: %s", file.getEmployment()));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");
            r.append("</tr>");

            r.append("<tr>");
            r.append("<td text-align= 'left' width= '50%'>");
            String fecha = PlusDate.plusDate(file.getAdmission_Date());
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Fecha de ingreso: %s", fecha));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");

            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Años laborados sector público: %s", file.getPublic_years()));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");
            r.append("</tr>");

            r.append("<tr>");
            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Correo electrónico: %s", file.getEmail()));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");

            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Teléfono: %s", file.getPhone()));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");
            r.append("</tr>");

            r.append("<tr>");
            r.append("<td text-align= 'left' width= '50%'>");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Salario: %s", file.getSalary()));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");

            r.append("<td text-align= 'left' width= '50%' >");
            r.append("<p>");
            r.append("<label>");
            r.append(String.format("Pluses: %s", " "));
            r.append("</label>");
            r.append("</p>");
            r.append("</td>");
            r.append("</tr>");
            r.append("</table><br>");

            r.append(String.format("<h2>" + "%s" + "</h2>", "Permisos con goce Salarial"));
            r.append(String.format("<h6>" + "%s" + "</h6>", "(posibilidad del trabajador de obtener el permiso solicitado sin que exista un perjuicio en su salario,  es decir, sin que exista ningún rebajo en su remuneración por el tiempo no laborado\n)"));
            List<LicenseCGS> cgs = file.getCgs();
            for (LicenseCGS licCGS : cgs) {
                r.append("<table  border='1' width= '100%'>");
                r.append("<tr>");
                r.append("<td text-align= 'left' width= '50%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Número Solicitud: %d", licCGS.getNumber_License()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");

                r.append("<td text-align= 'left' width= '50%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Categoria: %s", licCGS.getCategory()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td text-align= 'left' width= '50%'>");
                String startCGS = PlusDate.plusDate(licCGS.getStart_Date());
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Fecha de Inicio: %s", startCGS));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");

                r.append("<td text-align= 'left' width= '50%'>");
                String endCGS = PlusDate.plusDate(licCGS.getFinal_Date());
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Fecha final: %s", endCGS));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td text-align= 'left' width= '50%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Justificación: %s", licCGS.getJustification()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");
                r.append("</table><br>");
            }

            r.append(String.format("<h2>" + "%s" + "</h2>", "Permisos sin goce Salarial"));
            r.append(String.format("<h6>" + "%s" + "</h6>", "(corresponderán a aquellos permisos que el patrono otorga al trabajador en los que se realiza el rebajo correspondiente del tiempo de su ausencia.\n)"));
            List<LicenseSGS> sgs = file.getSgs();
            for (LicenseSGS licSGS : sgs) {
                r.append("<table  border='1' width= '100%'>");
                r.append("<tr>");
                r.append("<td text-align= 'left' width= '50%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Número Solicitud: %d", licSGS.getNumber_License()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");

                r.append("<td text-align= 'left' width= '50%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Categoria: %s", licSGS.getCategory()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td text-align= 'left' width= '50%'>");
                String startSGS = PlusDate.plusDate(licSGS.getStart_Date());
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Fecha de Inicio: %s", startSGS));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");

                r.append("<td text-align= 'left' width= '50%'>");
                String endSGS = PlusDate.plusDate(licSGS.getFinal_Date());
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Fecha final: %s", endSGS));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td text-align= 'left' width= '50%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Justificación: %s", licSGS.getJustification()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");
                r.append("</table><br>");
            }

            r.append(String.format("<h2>" + "%s" + "</h2>", "Permisos de Vacaciones"));
            r.append(String.format("<h6>" + "%s" + "</h6>", "(son un derecho y una necesidad biológica de toda persona trabajadora,  que tiene como propósito permitir a la persona trabajadora reponer el desgaste de energías realizado\n)"));
            List<Vacations> vacations = file.getVacations();
            for (Vacations result : vacations) {
                r.append("<table  border='1' width= '100%'>");
                r.append("<tr>");
                r.append("<td text-align= 'left' width= '100%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Número Solicitud: %d", result.getVacations_ID()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td text-align= 'left' width= '100%'>");
                String startVac = PlusDate.plusDate(result.getStart_Date());
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Fecha de Inicio: %s", startVac));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");

                r.append("<td text-align= 'left' width= '100%'>");
                String endVac = PlusDate.plusDate(result.getFinal_Date());
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Fecha final: %s", endVac));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");
                r.append("</table><br>");
            }

            r.append(String.format("<h2>" + "%s" + "</h2>", "Permisos por Incapacidad"));
            r.append(String.format("<h6>" + "%s" + "</h6>", "(Se entiende por incapacidad , aquella que según el cuadro agudo de la\n"
                    + "enfermedad o lesión que presente el funcionario, le impida desempeñar su capacidad laboral por un tiempo determinado.)"));
            List<LaboraInhability> inhabilities = file.getInhability();
            for (LaboraInhability result : inhabilities) {
                r.append("<table  border='1' width= '100%'>");
                r.append("<tr>");
                r.append("<td text-align= 'left' width= '100%'>");
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Número Solicitud: %d", result.getNumber_Inhability()));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td text-align= 'left' width= '100%'>");
                r.append("<p class=\"etiqueta\">Comprobante:&nbsp;</p>");
                r.append(String.format("<p><img src=\"../CargaComprobante?numberInhability=%d\"/ width= '500px'></p>",
                        result.getNumber_Inhability()));
                r.append("</td>");
                r.append("</tr>");

                r.append("<tr>");
                r.append("<td text-align= 'left' width= '100%'>");
                String endInha = PlusDate.plusDate(result.getMainDate());
                r.append("<p>");
                r.append("<label>");
                r.append(String.format("Fecha final: %s", endInha));
                r.append("</label>");
                r.append("</p>");
                r.append("</td>");
                r.append("</tr>");
                r.append("</table><br>");
            }
        }
        return r.toString();
    }

    public void updateHoliday(Holidays hol) {
        ResteasyWebTarget target = client.target("http://localhost:8083/api/holiday");
        Holidays ad = target.request().put(Entity.entity(hol, MediaType.APPLICATION_JSON),
                Holidays.class);
        System.out.println(ad.toString());
    }

    public String getInfoActHoliday(int id) {
        StringBuilder r = new StringBuilder();
        List<Holidays> holidays = allHolidays();
        for (Holidays holiday : holidays) {
            if (holiday.getIdHolidays() == id) {

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Día:");
                r.append("</td>");
                r.append("<td>");
                r.append("<select id = \"dia\" name = \"dia\">");

                r.append(String.format("<option value='%s'>%s</option>", holiday.getDay(), holiday.getDay()));
                r.append(String.format("<option value='%s'>%s</option>", "1", "1"));
                r.append(String.format("<option value='%s'>%s</option>", "2", "2"));
                r.append(String.format("<option value='%s'>%s</option>", "3", "3"));
                r.append(String.format("<option value='%s'>%s</option>", "4", "4"));
                r.append(String.format("<option value='%s'>%s</option>", "5", "5"));
                r.append(String.format("<option value='%s'>%s</option>", "6", "6"));
                r.append(String.format("<option value='%s'>%s</option>", "7", "7"));
                r.append(String.format("<option value='%s'>%s</option>", "8", "8"));
                r.append(String.format("<option value='%s'>%s</option>", "9", "9"));
                r.append(String.format("<option value='%s'>%s</option>", "10", "10"));
                r.append(String.format("<option value='%s'>%s</option>", "11", "11"));
                r.append(String.format("<option value='%s'>%s</option>", "12", "12"));
                r.append(String.format("<option value='%s'>%s</option>", "13", "13"));
                r.append(String.format("<option value='%s'>%s</option>", "14", "14"));
                r.append(String.format("<option value='%s'>%s</option>", "15", "15"));
                r.append(String.format("<option value='%s'>%s</option>", "16", "16"));
                r.append(String.format("<option value='%s'>%s</option>", "17", "17"));
                r.append(String.format("<option value='%s'>%s</option>", "18", "18"));
                r.append(String.format("<option value='%s'>%s</option>", "19", "19"));
                r.append(String.format("<option value='%s'>%s</option>", "20", "20"));
                r.append(String.format("<option value='%s'>%s</option>", "21", "21"));
                r.append(String.format("<option value='%s'>%s</option>", "22", "22"));
                r.append(String.format("<option value='%s'>%s</option>", "23", "23"));
                r.append(String.format("<option value='%s'>%s</option>", "24", "24"));
                r.append(String.format("<option value='%s'>%s</option>", "25", "25"));
                r.append(String.format("<option value='%s'>%s</option>", "26", "26"));
                r.append(String.format("<option value='%s'>%s</option>", "27", "27"));
                r.append(String.format("<option value='%s'>%s</option>", "28", "28"));
                r.append(String.format("<option value='%s'>%s</option>", "29", "29"));
                r.append(String.format("<option value='%s'>%s</option>", "30", "30"));
                r.append(String.format("<option value='%s'>%s</option>", "31", "31"));

                r.append("</select>");
                r.append("</td>");
                r.append("</tr");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Mes:");
                r.append("</td>");
                r.append("<td>");
                r.append("<select id = \"mes\" name = \"mes\">");

                r.append(String.format("<option value='%s'>%s</option>", holiday.getMonth(),
                        GetMonth.getMonth(holiday.getMonth())));
                r.append(String.format("<option value='%s'>%s</option>", "1", "Enero"));
                r.append(String.format("<option value='%s'>%s</option>", "2", "Febrero"));
                r.append(String.format("<option value='%s'>%s</option>", "3", "Marzo"));
                r.append(String.format("<option value='%s'>%s</option>", "4", "Abril"));
                r.append(String.format("<option value='%s'>%s</option>", "5", "Mayo"));
                r.append(String.format("<option value='%s'>%s</option>", "6", "Junio"));
                r.append(String.format("<option value='%s'>%s</option>", "7", "Julio"));
                r.append(String.format("<option value='%s'>%s</option>", "8", "Agosto"));
                r.append(String.format("<option value='%s'>%s</option>", "9", "Septiembre"));
                r.append(String.format("<option value='%s'>%s</option>", "10", "Octubre"));
                r.append(String.format("<option value='%s'>%s</option>", "11", "Noviembre"));
                r.append(String.format("<option value='%s'>%s</option>", "12", "Diciembre"));
                r.append("</select>");
                r.append("</td>");
                r.append("</tr");

                r.append("<tr>");
                r.append("<td class=\"etiqueta\">Nombre:");
                r.append("</td>");
                r.append("<td>");
                r.append(String.format("<input id = \"nombre\" name = \"nombre\" type = \"text\" value='%s'/>",
                        holiday.getName()));

                r.append("</td>");
                r.append("</tr");

                r.append("<tr>");
                r.append("<td colspan=\"1\" class=\"boton\">");
                r.append(String.format("<button type=\"submit\" class=\"boton\" value='%s' name='%s'>"
                        + "Enviar</button>", holiday.getIdHolidays(), "idHoliday"));
                r.append("</td>");

                r.append("<td colspan=\"1\" class=\"boton1\">");
                r.append("<button type=\"reset\" class=\"boton1\"><a href=\"listHoliday.jsp\" "
                        + "style=\"color:white; text-decoration: none;\">Cancelar</a></button>");
                r.append("</td>");

                r.append("</tr>");
            }
        }
        //r.append("</tbody>");
        return r.toString();
    }

}
