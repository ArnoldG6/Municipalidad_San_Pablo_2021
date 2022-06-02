
<%@page import="java.util.Objects"%>
<%@page import="common.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession(true).getAttribute("user");
    if(Objects.isNull(user)) {
        request.getRequestDispatcher("../common/nouser.jsp").forward(request, response);
    } else if(!User.validateBudgetAdminRol(user) && !User.validateSuperAdminRol(user)) {
        request.getRequestDispatcher("../common/invaliduser.jsp").forward(request, response);

    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

        <link href="../assets/vendor/bootstrap-5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/vendor/bootstrap-icons-1.8.1/bootstrap-icons.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/document.css" rel="stylesheet" type="text/css"/>

        <title>Certificado</title>

    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div id="sideb" class="col-sm-auto sticky-top">
                    <div  class="d-flex flex-sm-column flex-row flex-nowrap align-items-center sticky-top">
                        <div class="dropdown">
                            <a href="#" class="d-flex align-items-center justify-content-center p-3 link-dark text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi-person-circle h2"></i>
                            </a>
                            <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser3">
                                <li><a class="dropdown-item text-dark" href="../code-certifications/requests.jsp">Regresar a la página anterior</a></li>
                                <li><a class="dropdown-item text-dark" href="#" onclick="logout()">Cerrar Sesión...</a></li>

                            </ul>
                        </div>
                        <ul class="nav nav-pills nav-flush flex-sm-column flex-row flex-nowrap mb-auto mx-auto text-center align-items-center">
                            <li class="nav-item">
                                <label for="doc1" class="nav-link py-3 px-2 lb" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Agregar imagen(es) de centros de costos ">
                                    <i class="add btnIcon bi bi-plus-circle"></i>
                                </label>
                                <input type="file" id="doc1" onclick="addCC()">
                            </li> 
                            <li>
                                <a href="#" class="nav-link py-3 px-2" title="" onclick="deleteCC()" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Eliminar imagen(es) de centros de costos ">
                                    <i class="dlt btnIcon bi bi-trash3"></i>
                                </a> 
                            </li>
                            <li>
                                <a href="#" class="nav-link py-3 px-2" title="" onclick="genPDF()" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Descargar PDF para firmar">
                                    <i class="add btnIcon bi bi-file-earmark-pdf"></i>
                                </a>
                            </li>
                            <li>
                                <label for="doc3" class="nav-link py-3 px-2 lb" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Enviar solicitud ">
                                    <i class="add  btnIcon bi bi-send"></i>
                                </label>
                                <form id="formCsp" method="post">
                                    <input type="file" name="doc3" id="doc3" accept=".pdf" onchange="sendPDF()">
                                    <input name="idRequest" style="display: none" id="idRequest">
                                </form>
                            </li>

                        </ul>

                    </div>
                </div>
                <div id="content-area" class="col-sm p-3 min-vh-100">
                    <!-- content -->
                    <div class="navbar bg-light pt-0 pb-0">
                        <div class="container-fluid">
                            <img id="imagen">
                        </div>
                    </div>
                    <!--first textarea-->

                    <div class="container-fluid bg-light">
                        <div class="title">
                            <h6 class="pb-0">MUNICIPALIDAD DE SAN PABLO DE HEREDIA</h6>  
                            <h6 class="pb-0">TESORERIA MUNICIPAL</h6> 
                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="subtitle me-5 mt-3">
                            <p id="ptxt" class="text-success hide pt"></p>
                            <form name="form">
                                <textarea name="ta" class="st t-area" row="1" col="1" autofocus>Certificado de Código Presupuestario</textarea>
                            </form>
                            <h6 id="date" class="pb-4">San Pablo de Heredia, </h6> 
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="subtitle2 me-5 mt-3">
                            <p id="ptxt2" class="text-success hide pt"></p>
                            <form name="form2">
                                <textarea name="ta2" class="st t-area">
El suscrito Tesorero de la Municipalidad San Pablo de Heredia certifica que el saldo de la siguiente cuenta de Egresos es como se detalla a continuación:                        
                                </textarea>
                            </form>
                        </div>
                    </div>

                    <div class="mb-3 ms-5 fbtn" >
                        <div id="imgDiv">

                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="subtitle2 me-5 mt-3">
                            <textarea style="overflow:hidden !important;" class="st">
                                             Se extiende la presente en la ciudad de San Pablo de Heredia 

                                                ____________________________________________________________
                                                                            Tesorero Municipal
                                                                                  Firma y Sello
                            </textarea>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>

        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.0/html2pdf.bundle.min.js"></script>

        <script src="../assets/js/logout.js" type="text/javascript"></script>
        <script src="../assets/js/code-certifications/ccp-document.js" type="text/javascript"></script>

    </body>
</html>