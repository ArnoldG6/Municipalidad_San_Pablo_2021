<%@page import="java.util.Objects"%>
<%@page import="common.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession(true).getAttribute("user");
    if(Objects.isNull(user)) {
        request.getRequestDispatcher("../common/nouser.jsp").forward(request, response);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>SIGEP</title>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.datatables.net/1.11.3/css/dataTables.semanticui.min.css" rel="stylesheet" type="text/css"/>

        <link href="../assets/vendor/bootstrap-5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/menu/menu.css" rel="stylesheet" type="text/css">
        <link href="../assets/vendor/bootstrap-icons-1.8.1/bootstrap-icons.css" rel="stylesheet" type="text/css">

    </head>
    <%
        if(User.validateSuperAdminRol(user) || User.validateBudgetAdminRol(user)) {
    %>
    <body onload="retrieveAsAdmin();">

        <% } else { %>
    <body onload="retrieveAsUser();">
        <% } %>
        <jsp:directive.include file="../common/header.jsp"/>
        <main class="mt-5">
            <h1 class="d-flex justify-content-center"><span>Solicitudes Certificados de Saldo</span></h1>
            <div class="container d-flex justify-content-center">
                <table id="requests-list" class="table table-bordered table-hover compact" style="width:100%">
                    <thead>
                        <tr>
                            <th>Fecha</th> 
                            <th>Código de solicitud</th> 
                            <th>Solicitante</th>
                            <th>Departamento</th>
                            <th>Código presupuestario</th>
                            <th id="avaiable-column">Presupuesto disponible</th>
                            <th>Descripción</th> 
                            <th>Estado</th> 
                            <th>Monto solicitado</th>
                            <th id="last-column"></th>
                        </tr>
                    </thead>
                </table>
            </div>
        </main>
        <%
      if(User.validateSuperAdminRol(user) || User.validateBudgetAdminRol(user)) {
        %>
        <div class="modal fade" id="contenedor-modal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Solicitud de certificación de saldo presupuestario</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p id="info"><i class="fas fa-info-circle"></i> Los datos que se muestran a continuación son solo de lectura, no se pueden editar</p>

                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" style="width: 30%;">Nombre</span>
                            <input disabled id="nombre" type="text" class="form-control" aria-label="nombre" aria-describedby="addon-wrapping">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" style="width: 30%;">Departamento</span>
                            <input disabled id="departamento" type="text" class="form-control" aria-label="departamento" aria-describedby="addon-wrapping">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" style="width: 30%;">Código de partida</span>
                            <input disabled id="codigo" type="text" class="form-control" aria-label="codigo" aria-describedby="addon-wrapping">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" style="width: 30%;">Monto disponible</span>
                            <input disabled id="montoDis" type="text" class="form-control" aria-label="monto" aria-describedby="addon-wrapping">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" style="width: 30%;">Descripcion</span>
                            <input disabled id="descripcion" type="text" class="form-control" aria-label="descripcion" aria-describedby="addon-wrapping">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" style="width: 30%;">Monto Solicitado</span>
                            <input disabled id="monto" type="text" class="form-control" aria-label="monto" aria-describedby="addon-wrapping">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" style="width: 30%;">Codigo de solicitud</span>
                            <input disabled id="consecutivo" type="text" class="form-control" aria-label="consecutivo" aria-describedby="addon-wrapping">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-outline-success btn-sm " id="buildCertificateButton" data-bs-dismiss="modal" onclick="buildCertificate()" >Emitir Certificado</button> 
                        <button class="btn btn-outline-danger btn-sm" id="denegateButton" data-bs-toggle="modal" data-bs-target="#contenedor-modal3" data-bs-dismiss="modal">Denegar Solicitud </button>
                    </div>
                </div>
            </div>
        </div> 

        <!-- Contenido de la ventana modal para denegar solicitud-->
        <div class="modal fade" id="contenedor-modal3" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"> Solicitud Denegada</h5>
                        <button type="button"  class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" onclick="closeNotification()"></button>
                    </div>
                    <div class="modal-body">
                        <div id="bodySolicitud3">
                            <div class="input-group">
                                <p>Por favor agregue el motivo por el cual desea denegar la solicitud</p>
                                <br>
                                <span class="input-group-text">Motivo: </span>
                                <textarea id="textArea" class="form-control" maxlength="100" aria-label="With textarea" onkeyup="charCount()">Fondos Insuficientes</textarea>
                            </div>
                            <span id="textAreaCount" class="textAreaCount">0/100 (Máximo 100 caracteres) </span>
                            <br> 
                            <div id="liveAlertPlaceholder" class="hide">

                            </div>
                        </div>
                    </div>
                    <div  class="modal-footer">

                        <form id="form-denegate" name="form-denegate" style="display:none;" action="${pageContext.request.contextPath}/DenegateSCSP" method="post">
                            <input name="reason" id="reason">
                            <input name="scsp" id="scsp">
                        </form>
                        <button id="btnEnviar" onclick="denegateRequest();" class="btn btn-outline-success btn-lg " data-dismiss="modal">Enviar</button> 
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        <jsp:directive.include file="../common/footer.jsp"/>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>

        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.semanticui.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="../assets/js/logout.js" type="text/javascript"></script>

        <script src="../assets/js/balance-certifications/requests.js"></script>


    </body>
</html>
