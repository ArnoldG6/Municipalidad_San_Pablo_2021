/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Logica.Data.Modelo;
import Logica.Persona.Estudiante;
import Logica.Persona.Solicitante;
import Logica.Solicitud.SolicitudAyudaTemporal;
import Logica.Solicitud.SolicitudBecaAcademica;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "solicitud", urlPatterns = {
    "/API/solicitud/ayuda_temporal",
    "/API/solicitud/beca_academica",
    "/API/solicitud/ayuda_temporal/actualizar",
    "/API/solicitud/beca_academica/actualizar",
    "/API/solicitud/consultar-estado-solicitud",
    "/API/solicitud/consultar_cedula",
    "/API/solicitud/select_all_ayudas_temporales",
    "/API/solicitud/select_all_becas_academicas",
    "/API/solicitud/select_all_ayudasTemporales_por_solicitante",
    "/API/solicitud/select_all_becasAcademicas_por_solicitante",
    "/API/solicitud/get_solicitud_ayuda_temporal",
    "/API/solicitud/all-solicitudes-ayudas-temporales",
    "/API/solicitud/all-solicitudes-becas-academicas"
})

public class Servidor extends HttpServlet {

    /**
     * Processes requests for <code>GET</code>, <code>POST</code>
     * ,<code>OPTIONS</code>, <code>PUT</code>, <code>DELETE</code>,
     * <code>UPDATE</code> HTTP methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD,UPDATE");
            switch (request.getServletPath()) {
                case "/API/solicitud/ayuda_temporal":
                    crearSolicitudAyudaTemporal(request, response);
                    break;
                case "/API/solicitud/beca_academica":
                    crearSolicitudBecaAcademica(request, response);
                    break;
                case "/API/solicitud/ayuda_temporal/actualizar":
                    actualizarSolicitudAyudaTemporal(request, response);
                    break;
                case "/API/solicitud/beca_academica/actualizar":
                    actualizarSolicitudBecaAcademica(request, response);
                    break;
                case "/API/solicitud/consultar-estado-solicitud":
                    consultarEstadoSolicitud(request, response);
                    break;
                case "/API/solicitud/consultar_cedula":
                    consultarCedula(request, response);
                    break;
                case "/API/solicitud/select_all_ayudas_temporales":
                    selectAllAyudasTemporales(request, response);
                    break;
                case "/API/solicitud/select_all_becas_academicas":
                    selectAllBecasAcademicas(request, response);
                    break;
                case "/API/solicitud/select_all_ayudasTemporales_por_solicitante":
                    selectAllAyudasTemporalesBySolicitante(request, response);
                    break;
                case "/API/solicitud/select_all_becasAcademicas_por_solicitante":
                    selectAllBecasAcademicasBySolicitante(request, response);
                    break;
                case "/API/solicitud/all-solicitudes-ayudas-temporales":
                    selectAllSolicitudesAyudasTemporales(request, response);
                    break;
                case "/API/solicitud/all-solicitudes-becas-academicas":
                    selectAllSolicitudesBecasAcademicas(request, response);
                    break;
            }
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response is the object that obtains the requested JSON formatted
     * 'Risk' object. searchRisk method verifies the user's identity and returns
     * a JSON formatted 'Risk' object that matches with the search data sent by
     * the client with any of the 'Risk' class attributes..
     */
    private void crearSolicitudAyudaTemporal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        SolicitudAyudaTemporal at = new Gson().fromJson(requestJSON, SolicitudAyudaTemporal.class);
        Modelo.getInstancia().createDireccion(at.getDireccion());
        if (Modelo.getInstancia().createSolicitante(at.getSolicitante())) {
            Solicitante s = Modelo.getInstancia().getSolicitanteById(at.getSolicitante().getCedula()).get().get();
            at.getAyudaTemporal().setIdSolicitante(s.getIdPersona());
        } else {
            Solicitante s = Modelo.getInstancia().getSolicitanteById(at.getSolicitante().getCedula()).get().get();
            Modelo.getInstancia().createSolicitante(s);
            at.getAyudaTemporal().setIdSolicitante(Modelo.getInstancia().getLastSolicitanteId());
        }
        at.getAyudaTemporal().setIdDireccion(Modelo.getInstancia().getLastDireccionId());
        Modelo.getInstancia().createAyudaTemporal(at.getAyudaTemporal());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void crearSolicitudBecaAcademica(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        SolicitudBecaAcademica ba = new Gson().fromJson(requestJSON, SolicitudBecaAcademica.class);
        Modelo.getInstancia().createDireccion(ba.getDireccion());
        if (Modelo.getInstancia().createSolicitante(ba.getSolicitante())) {
            Solicitante s = Modelo.getInstancia().getSolicitanteById(ba.getSolicitante().getCedula()).get().get();
            ba.getBecaAcademica().setIdSolicitante(s.getIdPersona());
        } else {
            Solicitante s = Modelo.getInstancia().getSolicitanteById(ba.getSolicitante().getCedula()).get().get();
            Modelo.getInstancia().createSolicitante(s);
            ba.getBecaAcademica().setIdSolicitante(Modelo.getInstancia().getLastSolicitanteId());
        }

        if (Modelo.getInstancia().createEstudiante(ba.getEstudiante())) {
            Estudiante e = Modelo.getInstancia().getEstudianteById(ba.getEstudiante().getCedula()).get().get();
            ba.getBecaAcademica().setIdEstudiante(e.getIdPersona());
        } else {
            Estudiante e = Modelo.getInstancia().getEstudianteById(ba.getEstudiante().getCedula()).get().get();
            Modelo.getInstancia().createEstudiante(e);
            ba.getBecaAcademica().setIdEstudiante(Modelo.getInstancia().getLastEstudianteId());
        }
        ba.getBecaAcademica().setIdDireccion(Modelo.getInstancia().getLastDireccionId());
        Modelo.getInstancia().createBecaAcademica(ba.getBecaAcademica());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void actualizarSolicitudAyudaTemporal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        SolicitudAyudaTemporal at = new Gson().fromJson(requestJSON, SolicitudAyudaTemporal.class);
        Modelo.getInstancia().updateDireccion(at.getDireccion());
        Modelo.getInstancia().updateSolicitante(at.getSolicitante());
        Modelo.getInstancia().updateAyudaTemporal(at.getAyudaTemporal());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void actualizarSolicitudBecaAcademica(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        SolicitudBecaAcademica ba = new Gson().fromJson(requestJSON, SolicitudBecaAcademica.class);
        Modelo.getInstancia().updateDireccion(ba.getDireccion());
        Modelo.getInstancia().updateSolicitante(ba.getSolicitante());
        Modelo.getInstancia().updateEstudiante(ba.getEstudiante());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void consultarEstadoSolicitud(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson(Modelo.getInstancia().getEstadoSolicitudByClaveRecuperacion(request.getParameter("claveRecuperacion")));
        if ("0".equals(responseJSON)) {
            String badResponse = "No se pudo obtener el estado";
            response.getWriter().write(new Gson().toJson(badResponse));
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void consultarCedula(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson((Modelo.getInstancia().getSolicitanteById(request.getParameter("id"))).get());
        if (responseJSON == null) {
            String badResponse = "No se pudo get el estado";
            response.getWriter().write(badResponse);
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void selectAllAyudasTemporales(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson(Modelo.getInstancia().getAllAyudasTemporales());
        if (responseJSON == null) {
            String badResponse = "No se pudo get el estado";
            response.getWriter().write(badResponse);
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void selectAllBecasAcademicas(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson(Modelo.getInstancia().getAllBecasAcademicas());
        if (responseJSON == null) {
            String badResponse = "No se pudo get el estado";
            response.getWriter().write(badResponse);
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void selectAllAyudasTemporalesBySolicitante(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson(Modelo.getInstancia().getAllAyudasTemporalesByIdSolicitante(request.getParameter("id")));
        if (responseJSON == null) {
            String badResponse = "No se pudo get el estado";
            response.getWriter().write(badResponse);
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void selectAllBecasAcademicasBySolicitante(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson(Modelo.getInstancia().getAllBecasAcademicasBySolicitanteId(request.getParameter("id")));
        if (responseJSON == null) {
            String badResponse = "No se pudo get el estado";
            response.getWriter().write(badResponse);
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void selectAllSolicitudesAyudasTemporales(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson(Modelo.getInstancia().selectAllSolicitudesAyudasTemporales());
        if (responseJSON == null) {
            String badResponse = "No se pudo get el estado";
            response.getWriter().write(badResponse);
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void selectAllSolicitudesBecasAcademicas(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseJSON = new Gson().toJson(Modelo.getInstancia().selectAllSolicitudesBecasAcademicas());
        if (responseJSON == null) {
            String badResponse = "No se pudo get el estado";
            response.getWriter().write(badResponse);
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
