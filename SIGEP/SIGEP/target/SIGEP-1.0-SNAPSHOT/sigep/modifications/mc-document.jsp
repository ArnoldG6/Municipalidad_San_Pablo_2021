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
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <link href="../assets/vendor/bootstrap-5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/vendor/bootstrap-icons-1.8.1/bootstrap-icons.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/document.css" rel="stylesheet" type="text/css"/>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.datatables.net/1.11.3/css/dataTables.semanticui.min.css" rel="stylesheet" type="text/css"/>

        <title>Resolución</title>
    </head>
    <body onload="retrieveMovementsData();">
        <div class="container-fluid">
            <div class="row">
                <div id="sideb" class="col-sm-auto sticky-top">
                    <div  class="d-flex flex-sm-column flex-row flex-nowrap align-items-center sticky-top">
                        <div class="dropdown">
                            <a href="#" class="d-flex align-items-center justify-content-center p-3 link-dark text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi-person-circle h2"></i>
                            </a>
                            <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser3">
                                <li><a class="dropdown-item text-dark" href="../modifications/approved-requests.jsp">Regresar a la página anterior</a></li>
                                <li><a class="dropdown-item" onclick="logout()">Cerrar Sesión...</a></li>

                            </ul>
                        </div>
                        <ul class="nav nav-pills nav-flush flex-sm-column flex-row flex-nowrap mb-auto mx-auto text-center align-items-center">
                            
<!--                            <li class="nav-item">
                                <label for="doc1" class="nav-link py-2 px-2 lb" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Agregar imagen(es) del resumen ">
                                    <i class="add btnIcon bi bi-plus-circle"></i>
                                </label>
                                <input type="file" id="doc1" onclick="addSummary()">
                            </li> 
                            <li>
                                <a href="#" class="nav-link py-2 px-2" title="" onclick="deleteSummary()" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Eliminar imagen(es) del resumen ">
                                    <i class="dlt btnIcon bi bi-trash3"></i>
                                </a> 
                            </li>
                            <li>
                                <label for="doc2" class="nav-link py-2 px-3 lb" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Agregar imagen(es) detalles">
                                    <i class="add btnIcon bi bi-plus-circle"></i>
                                </label>
                                <input type="file" id="doc2" onclick="addDetails()">
                            </li>

                            <li>
                                <a href="#" class="nav-link py-2 px-2" title="" onclick="deleteDetails()" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Eliminar imagen(es) detalles ">
                                    <i class="dlt btnIcon bi bi-trash3"></i>
                                </a>
                            </li> -->

                            <li>
                                <label for="doc4" class="nav-link py-2 px-3 lb" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Agregar anexos">
                                    <i class="add btnIcon bi bi-plus-circle"></i>
                                </label>
                                <input type="file" id="doc4" onclick="addAnnexes()">
                            </li>
                            <li>
                                <a href="#" class="nav-link py-2 px-2" title="" onclick="deleteAnnexes()" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Eliminar anexos ">
                                    <i class="dlt btnIcon bi bi-trash3"></i>
                                </a>
                            </li>

                            <li>
                                <a href="#" class="nav-link py-2 px-2" title="" onclick="genPDF()" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Descargar PDF para firmar">
                                    <i class="add btnIcon bi bi-file-earmark-pdf"></i>
                                </a>
                            </li>
                            <li>
                                <label for="doc3" class="nav-link py-2 px-2 lb" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Enviar solicitud ">
                                    <i class="add  btnIcon bi bi-send"></i>
                                </label>
                                <form id="formMc" method="post">
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
                            <h6 class="pb-0">PLANIFICACIÓN, CONTROL Y PRESUPUESTO</h6> 
                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="subtitle me-5 mt-3">
                            <p id="ptxt" class="text-success hide pt"></p>
                            <form name="form">
                                <textarea name="ta" class="st t-area" row="1" col="1" autofocus>OFICIO MSPH-PCP-INT-MOD-001 (click aquí para modificar el texto)
                                </textarea>
                            </form>
                            <h6 id="date" class="pb-4">San Pablo de Heredia, </h6> 
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="subtitle1 me-5">     
                            <p id="ptxt1" class="text-success hide pt"></p>
                            <form name="form1">
                                <textarea name="ta1" class="st t-area">
Srs.

Concejo Municipal

Municipalidad de San Pablo de Heredia

Estimables Señores	

RERERENCIA: MODIFICACION AL PRESUPUESTO No 1-2021
Conforme el dictado de la normativa vigente, Código Municipal y legislación conexa, se somete a consideración de
Concejo Municipal, la Modificación 1-2021,  al Presupuesto Ordinario 2021, por un monto de:
234.365.959,80 (doscientos treinta y cuatro millones trescientos sesenta y cinco mil novecientos
cincuenta y nueve con  80/100) colones, conforme el siguiente detalle:
       
                                </textarea>
                            </form>

                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="subtitle2 me-5 mt-3">
                            <p id="ptxt2" class="text-success hide pt"></p>
                            <form name="form2">
                                <textarea name="ta2" class="st t-area">
1.  RESUMEN DE LA MODIFICACION
(click aquí para modificar el texto)
                                </textarea>
                            </form>
                        </div>
                    </div>
                    <div class="mb-3 ms-5 fbtn" >
                        <table id="movements-list" class="table table-bordered table-hover compact" style="width:100%">
                            <thead>
                                <tr>
                                    <th scope="col">Código</th>
                                    <th scope="col">Partida decreciente</th>
                                    <th scope="col">Partida creciente</th>
                                    <th scope="col">Monto</th>
                                </tr>
                            </thead>
                        </table>
                        <div id="imgDiv">

                        </div>
                    </div>
                    <div>

                    </div>

                    <div class="container-fluid">
                        <div class="st a subtitle3 me-5 mt-3">
                            <p id="ptxt3" class="text-success hide pt"></p>
                            <form name="form3">
                                <textarea name="ta3" class="st t-area">
2.  DETALLE DE LA MODIFICACION POR PROGRAMA
(click aquí para modificar el texto)

                                </textarea>
                            </form>
                        </div>
                    </div>

                    <div class="mb-3 ms-5 fbtn" >         
                        <div id="imgDiv2">
                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="subtitle4 me-5 mt-3">
                            <p id="ptxt4" class="text-success hide pt"></p>
                            <form name="form4">
                                <textarea name="ta4" class="st t-area">
(click aquí para modificar el texto)

Se adjunta detalle con anexos
                                </textarea>
                            </form>
                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="subtitle5 me-5 mt-3">
                            <textarea class="st">
                ESPACIO PARA FIRMA
                (click aquí para modificar el texto)
                Bernardo Porras López
                Alcalde Municipal

                            </textarea>
                        </div>

                    </div>
                    <div class="container-fluid">
                        <div class="st a subtitle3 me-5 mt-3">
                            <p id="ptxt5" class="text-success hide pt"></p>
                            <form name="form5">
                                <textarea name="ta5" class="st t-area">
ANEXOS
(click aquí para modificar el texto)
                                </textarea>
                            </form>
                        </div>
                    </div>

                    <div class="mb-3 ms-5 fbtn" >
                        <div id="imgDiv3">
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <script src="../assets/vendor/jquery-3.6.0/js/jquery-3.6.0.min.js"></script>
        <script src="../assets/vendor/bootstrap-5.1.3/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.0/html2pdf.bundle.min.js"></script>

        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.semanticui.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

        <script src="../assets/js/logout.js" type="text/javascript"></script>
        <script src="../assets/js/modifications/mc-document.js" type="text/javascript"></script>
    </body>
</html>